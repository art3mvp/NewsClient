package com.art3mvp.newsclient.samples

import android.os.Handler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SideEffectTest(n: MyNum) {
    Column {
        LazyColumn {
            repeat(5) {
                item { Text(text = "${n.a}") }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Handler().postDelayed({ n.a = 5}, 3000)
        LazyColumn {
            repeat(5) {
                item { Text(text = "${n.a}") }
            }
        }
    }

}

data class MyNum(var a: Int)