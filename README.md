# VKClient

VkClient is a mini app within the VK ecosystem. This app is a simulation of a social media application, offering a seamless user experience across three primary screens: home screen, camera screen, and profile screen.

## Screens

### Home Screen
- Displays a feed of posts recommended to the user.
- Users can like posts and view comments on each post.
- The feed of posts can be refreshed with a pull.
- Users can left swipe to dislike a post, ensuring it is never recommended again.

### Camera Screen
- Integrated with CameraX Google library for capturing photos.
- Photos taken within the app are saved to both the app and the phone's gallery.
- New photos added through the phone's native camera are synchronized with the app.

### Profile Screen
- Displays the user's profile with cover photo, profile picture, and recent photos uploaded to VK.

## Technical Details

VkClient is built using modern Android development practices, including:
- **Compose UI** for declarative UI development.
- **Clean Architecture** and **MVVM** patterns for maintainability and testability.
- **Dependency Injection** for managing dependencies.
- **Compose Navigation** for navigating between screens.

## Tech Stack

- **Kotlin Coroutines** and **Flows** for asynchronous programming.
- **Retrofit** and **OkHttp** for making HTTP requests.
- **GSON** for JSON serialization and deserialization.
- **CameraX** for camera integration.
