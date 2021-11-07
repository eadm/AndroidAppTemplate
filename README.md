# Android App Template

This project is starting point for android development. It will help you to skip boilerplate code and to concentrate on writing business logic of your new app.

## Features
- Production ready structure
- Clean architecture
- UDF / Redux based presentation layer architecture independent from Android
- Kotlin stack
- Coroutines
- User data encryption (SharedPreferences + Room)
- Material theme ready
- Flipper debugger

## Architecture

### Project architecture
![DPUPUmR](https://user-images.githubusercontent.com/3856523/140655044-a712876e-72b8-4b18-885c-085cadc3e22e.png)

Project architecture is based on expanded clean architecture with 6 layers: view, presentation, domain, data, cache, remote. Read more in the following article.

[Android Architecture](https://hackmd.io/e207QR5VQ-yiKagxY3CSAA)

### Presentation architecture

Presentation architecture is based on UDF and inherits Redux architecture. This helps to avoid state inconsistency and also provides an ability to share presentation logic of your app between platforms via KMM as it is not bound to Android.
