package com.art3mvp.newsclient.data.repository

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.art3mvp.newsclient.domain.repository.CameraRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class CameraRepositoryImpl @Inject constructor(
    private val context: Context,
) : CameraRepository {

    private val uriExternal = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    private val contentResolver = context.contentResolver
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _bitmaps = mutableListOf<Bitmap>()
    private val bitmaps
        get() = _bitmaps.toList()

    private val loadLastEvent = MutableStateFlow(false)
    private val onLoadImageEvent = MutableSharedFlow<Unit>(replay = 1)
    private val loadedBitmaps = flow {
        onLoadImageEvent.emit(Unit)
        onLoadImageEvent.collect {

            val sortOrder = if (!loadLastEvent.value) {
                null
            } else {
                "${MediaStore.Images.Media.DATE_ADDED} DESC"
            }

            val cursor =
                contentResolver.query(
                    uriExternal, null, null, null, sortOrder
                )

            cursor?.use {
                val idColumn = it.getColumnIndexOrThrow(MediaStore.Images.Media._ID)

                while (it.moveToNext()) {
                    val imageId = it.getLong(idColumn)
                    val imageUri = Uri.withAppendedPath(uriExternal, imageId.toString())
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        val source =
                            ImageDecoder.createSource(context.contentResolver, imageUri)
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
                    }
                    _bitmaps.add(bitmap)
                    if (loadLastEvent.value) {
                        loadLastEvent.value = false
                        this.emit(bitmaps)
                        return@collect
                    }
                }
            }
            cursor?.close()
            this.emit(bitmaps)
        }
    }

    private val bitmapList = loadedBitmaps
        .stateIn(
            scope = scope,
            started = SharingStarted.Lazily,
            initialValue = emptyList()
        )


    private suspend fun saveBitmapToGallery(bitmap: Bitmap) {
        loadLastEvent.value = true
        val imageByteArray = bitmapToByteArray(bitmap)
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "img_${System.currentTimeMillis()}.png")
            put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
        }
        val imageUri = contentResolver.insert(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
        )
        imageUri?.let { uri ->
            contentResolver.openOutputStream(uri)?.use { outputStream ->
                outputStream.write(imageByteArray)
            }
        }
        onLoadImageEvent.emit(Unit)

    }

    override suspend fun reloadGallery() {
        _bitmaps.clear()
        onLoadImageEvent.emit(Unit)
    }

    override fun getBitmapList(): StateFlow<List<Bitmap>> = bitmapList

    override suspend fun onTakePhoto(bitmap: Bitmap) {
        saveBitmapToGallery(bitmap)
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }
}