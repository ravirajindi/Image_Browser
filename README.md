This is an image browsing demo application which was developed as part of coding challenge for interview purpose.

Steps to build and install apk
1. Open the project in Android Studio
2. Build project using Menu->Build->Make Project and/or click 'Run app' button in toolbar

Steps to use the application
1. Launch the app
2. Enter any search query in the search bar to search top images of the week from Imgur backend
3. Search result will be sorted in reverse chronological order
4. Toggle between List and Grid option using the switch in the top right corner.
5. List mode shows one item per row. List item includes image, title, image count and posted date
6. Grid mode shows adaptive number of item per row depending upon available space. 
7. Similar to List item, grid item also includes image, title, image count and posted date but the arrangement in List item and Grid item is different

Third-party libraries used
1. Retrofit
2. Coil
3. OkHttp (used by Retrofit)
4. Kotlin JSON Serialization
5. Kotlin Coroutines Test

Architectural decisions
1. Separate data and UI layer by using repository as single source of truth which consolidates data from multiple sources such as network v/s local database. (Room implementation is not covered and can be considered as TODO)
2. Dependency injection to provide instance of repository which is one of the recommended practice for Android
3. As a part of DI, injecting fake data for testing purpose
4. Keeping implementation of all the UI elements such as Composable outside of Activity which is mainly responsible for setting content
5. Keeping data class for serialization as light as possible since only fewer keys are actually used/required to show desired UI

TODO: 
1. Implement debouncing for search query
2. Store sensitive information used in app such as client id in secure manner
3. Handle mp4/gif urls in appropriate manner and get rid of the workaround which loads mp4/gif url as static image
4. Add 'Scroll To Top' option
