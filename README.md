Test assignment for Android application using Giphy API 🔗 (https://api.giphy.com/).

The application must consist of two screens:
 
- First screen:
1. Display gif images as a list/table.
2. Image uploads must be page-by-page.
3. Search images by keyboard input.
4. Offline mode - caching images to internal storage and links on images to a local database.
5. The ability to delete an image from local storage, while such the image should not be displayed in the list on the screen during subsequent server requests.

- Second screen:
1. Full screen display of the selected GIF
2. Ability to view other images using a horizontal swipe across the screen.

Requirments for task:
1. Consider screen flip
2. Apply MVP, MVVM, MVI... pattern
3. Use Dependency injection (Dagger, Koin...)
4. It is desirable to write in Kotlin
5. It will be a good plus to use RxJava2, kotlin coroutines, Android
architecture components.
6. Conduct development in the repository, at the end of the work - provide a link to repo.


 This project implements:
- MVVM with a clean architecture approach.
- Room for local database
- Retrofit for REST
- Hilt for Dependency injection
- Paging3 (RemoteMediator) for pagination and caching into local databse
- Viewpager2 for horizontal swipe
