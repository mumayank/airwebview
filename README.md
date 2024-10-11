<img src="./assets/logo.png" alt="Alt text" width="500"/>

# AirWebView 🚀

[![](https://jitpack.io/v/mumayank/airwebview.svg)](https://jitpack.io/#mumayank/airwebview)

✨ **AirWebView** is your go-to Android library that wraps a `composable` (and a `view`) for loading websites and `PDF`s from a `URL` in style! 🖥️📄

It combines the trusty [WebView](https://developer.android.com/reference/android/webkit/WebView) for websites 🌐 and the fabulous [AndroidPdfViewer](https://github.com/DImuthuUpe/AndroidPdfViewer) for `PDF`s, making your life way easier. 🎉

## Key difference 🔍

- **The big deal?** 🕵️‍♀️ Unlike other libraries that turn `PDF` pages into boring, static images, **AirWebView** keeps the links clickable and interactive! ✨ No more dead links! 🌈
- Using the powerful [AndroidPdfViewer](https://github.com/DImuthuUpe/AndroidPdfViewer), your `PDF` links stay alive and kickin' ⚡—all while caching the `PDF` for smoother loading. 🛠️

## Demo 🎥

[Watch the demo YouTube video](https://youtu.be/RugCPfDioWk) 👀

## Possible downsides 😬

⚠️ **Heads-up**: Including this library will bulk up your APK size by ~16MB. But no worries! If you use split `APK` or `aab` (which are the default anyway), you're all good. 👍 [source](https://github.com/DImuthuUpe/AndroidPdfViewer)

## Usage 🚀

### Project-level `build.gradle` 📄

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }  // Add this 🚀
    }
}
```

### App-level `build.gradle` 🎯

```gradle
dependencies {
    implementation 'com.github.mumayank:airwebview:+' // Add this magic 🪄
}
```

### Project-level `gradle.properties` 🛠️

```gradle
android.useAndroidX=true
android.enableJetifier=true
```

🆕 **Latest version:**  
[![](https://jitpack.io/v/mumayank/airwebview.svg)](https://jitpack.io/#mumayank/airwebview)

### Compose-based ⚛️

```kotlin
AirWebView(
    Modifier.fillMaxSize(), 
    url,
    onProgressChange = { 
        // Use 'it' to track progress 📈
    },
    onError = {
        // Uh oh! Handle error scenarios 😱
    },
    setCustomWebView = {
        // optional 🛠️
        // Customize your WebView if you want 🎨
    },
    setCustomPdfView = {
        // optional 🛠️
        // Customize your PDFView if you need to 📑
    }
)
```

### View-based 📺

In your XML layout:

```xml
<com.mumayank.airwebview.AirWebView
    android:id="@+id/airWebView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

In your fragment:

```kotlin
airWebView.load(
    context, 
    viewModelScope, 
    url,
    onProgressChange = {
        // Track that sweet progress 📊
    },
    onError = {
        // Whoops! Handle errors like a pro 💥
    },
    setCustomWebView = {
        // optional 🛠️
        // Tweak the WebView to your heart's desire 🧰
    },
    setCustomPdfView = {
        // optional 🛠️
        // Tweak the PDFView, you’re the boss here 👑
    }
)
```

## Motivation 💡

📄 Rendering PDFs natively in Android? Not that easy! 🤯 Here's why **AirWebView** was born:

### WebView limitations ❌

- WebView is great for websites, but it’s not a fan of `PDF`s. 🛑
- You’d usually have to rely on Google Drive’s URL trick, but that’s unreliable for several reasons (file size limits, user logins, Google’s mood swings, etc.) 🌧️

   Check these references if you're curious:  
   - [source1](https://support.google.com/drive/thread/167256653?hl=en&msgid=167271626)  
   - [source2](https://stackoverflow.com/a/10225157)  
   - [source3](https://mcbltd.atlassian.net/browse/OM-8599?focusedCommentId=2913245)

### Chrome custom tabs limitations 🚫

- Can’t render PDFs directly and also relies on the Google Drive URL trick. 🛠️
- Plus, if your user isn’t logged in to Chrome or Google, good luck loading that PDF! 😵

[Read more about it](https://developer.chrome.com/docs/android/custom-tabs/guide-get-started)

### PdfRenderer? 🤔

- The native `PdfRenderer` is cool, but... it turns PDFs into static images, making all the links useless. 🗿  
- Also, it's not thread-safe, meaning you’ll have to handle that headache yourself. 😓
- [AirPdf](https://github.com/mumayank/AirPdf)
    - I had created an OS library hosted on GitHub in 2023 to utilize PdfRenderer solution out
      of the box.
    - But it also inherits the same issue: URLs are not clickable because pages are rendered as
      bitmaps.
- Android 15
    - Android 15 introduced upgrades to PdfRenderer, adding support for password-protected PDFs,
      annotations, and form editing. However, these features are only available on Android 15+
      currently, making them inaccessible for most devices currently with our users. Clickable
      URLs remain unsupported, limiting the interactivity of the rendered PDFs.

### Popular 3rd party libs 🏗️

- **PSPDFKit**  
  - Super powerful, but… difficult to customize + it’s paid. 💸

- **AndroidPdfViewer**  
  - Adds 16MB to your app size and requires extra work of downloading and caching your PDF behind the scene before supplying it to this library for rendering PDFs. 😅

## Shoutout 🙌

A huge thanks to the awesome [AndroidPdfViewer](https://github.com/DImuthuUpe/AndroidPdfViewer) library for making rendering PDFs easy-peasy. 🎉

## Keywords 🔑 (SEO Magic) ✨

Android WebView library 🚀  
PDF viewer Android library 📄  
Android WebView PDF support 📱  
Render clickable PDF links ✅  
Load PDF from URL Android 🌐  
Android PDF rendering solution 🛠️  
Best WebView library for Android 🏆  
Web content rendering Android 🌐  
Android PDF viewer with links 🔗  
Load website in WebView Android 📲  
Android WebView alternative 🤖  
Android PDF viewer library 📑  
PDF viewer for Android apps 📱  
Android PDF renderer with URL support 📄  
WebView and PDF library for Android 📜  
Android WebView open-source 🛠️  
Open-source PDF viewer Android 🧰  
Best PDF rendering library for Android 🏅  
Lightweight PDF viewer Android 📂  
Android library load PDFs 📥  
Customizable WebView Android 🎨  
Android PDF view composable ⚛️  
Best PDF viewer library for Android 2024 📆  
PDF rendering Android app 📲  
Display PDFs in Android WebView 📑  
Render PDF with clickable links Android 🔗  
Android PDF support with clickable URLs ✅  
PDF rendering in Android apps 📱  
Android PDF WebView alternative 🤖  
Best PDF loader Android library 📦  
Load websites in Android apps 🌐  
Android PDF link support 🔗  
Android PDF viewer with URL links 🔗  
PDF rendering for Android developers 👩‍💻  
Android WebView wrapper 🛠️  
Android PDF viewer composable 📑  
Open-source WebView solution 🛠️  
Android WebView best practices ✅  
PDF loading library for Android 📥  
Load website with WebView Android 🌍  
WebView wrapper Android 🤖  
Android WebView code examples 📜  
Display PDFs in Android apps 📑  
Android library for website loading 📄  
WebView alternative Android solution 📱  
Load website in Android 🌐  
Android PDF viewer open source 🛠️  
Android WebView GitHub library 🐙  
Android WebView tutorial 📚  
WebView PDF support in Android 📄  
Android WebView wrapper solution 🛠️  
Android WebView performance optimization ⚡  
PDF viewer with interactive links Android 🔗  
Android WebView enhancements 🛠️  
Display website Android app 🌍  
WebView example for Android 📄  
Android WebView URL handling 🔗  
Android WebView rendering PDFs 📑  
Load URL in Android app 📲  
Android library for PDF viewing 📄  
Android PDF viewer with caching 📂  
PDF download and view Android 📥  
Android WebView optimizations 🚀  
Handle PDFs in Android app 📑  
WebView library for Android apps 📱  
Android WebView features 🔥  
Android PDF renderer with caching 📂  
Android WebView best library 2024 📆  
Android PDF viewer GitHub 🐙  
Interactive PDF viewer Android 🔗  
Android WebView link support 🔗  
Android PDF renderer open source 🛠️  
WebView for Android developers 👩‍💻  
Best Android library for WebView 🏆  
Android WebView integration 🔗  
Best Android WebView open-source 🛠️  
PDF viewer Android Jetpack Compose ⚛️  
Android WebView and PDF integration 📑  
Android PDF WebView hybrid 🤖  
Android WebView full screen 📲  
Android PDF viewer best practices ✅  
WebView Android GitHub 🐙  
Android WebView split APK 📦  
Handle PDF loading in Android 📱  
WebView library Android app 🌐



