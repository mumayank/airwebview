<img src="./assets/logo.png" alt="Alt text" width="800"/>

# AirWebView

[![](https://jitpack.io/v/mumayank/airwebview.svg)](https://jitpack.io/#mumayank/airwebview)

This library helps render/ load a website or PDF from a URL within the app.

Example: To show terms and conditions to the user.

___

## Demo

[Watch the demo YouTube video](https://youtu.be/rmAG69zXjPI)

---

## Features

- You just have to supply your URL to the library. It takes care of:
    - If the URL is a redirection URL, it internally traverses to the final URL
    - If the final URL is:
        - a website, it loads it in Android's WebView
        - a PDF:
            - it downloads the PDF in cache memory
            - and loads it in PDFView from [AndroidPdfViewer](https://github.com/DImuthuUpe/AndroidPdfViewer) library, which uses Google-AOSP's [PDFium](https://android.googlesource.com/platform/external/pdfium/) library internally.
    - progress and error callbacks are provided
    - uses coroutines
    - Compose and View both supported
    - minSdk = 21, compileSdk = 33

___

## Usage

Project-level build.gradle:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }  // Add this
    }
}
```

app-level build.gralde:

```gradle
dependencies {
    implementation 'com.github.mumayank:airwebview:+' // Add this
}
```

project-level gradle.properties:

```gradle
android.useAndroidX=true
android.enableJetifier=true
```

Latest version:

[![](https://jitpack.io/v/mumayank/airwebview.svg)](https://jitpack.io/#mumayank/airwebview)

---

### Compose-based

```kotlin
AirWebView(
    Modifier.fillMaxSize(),
    url,
    onProgressChange = {
        // Use 'it' to handle progress change
    },
    onError = {
        // Handle error scenario
    }
)
```

---

### View-based

layout xml:

```xml
<com.mumayank.airwebview.AirWebView
    android:id="@+id/airWebView"
    android:layout_width="match_parent"
    android:layout_height="match_parent" />
```

fragment:

```kotlin
// Show progress here
airWebView.load(
    context,
    viewModelScope,
    url,
    onProgressChange = {
        // Use 'it' to handle progress change
    },
    onError = {
        // Handle error scenario
    }
)
```

___

## Motivation

Challenges in rendering/ loading PDFs with other solutions:

### WebView

- Using Android's WebView to display websites is straightforward, but it cannot natively render
  PDFs.
- WebView is built for web content, not document processing, and lacks built-in PDF support.
- A common workaround involves using a Google Drive URL prefix to render PDFs through Google’s
  services.

- Limitations of this approach:
    - PDFs won't render if the user has exhausted the anonymous preview limit, which is undisclosed
      by Google.
    - File size limits: PDFs over 25MB won’t display.
    - PDFs won’t render if the user is not logged in.

- Additional challenges:
    - No control over UI/UX.
    - This method is not future-proof, as Google can remove access at any time.
    - There is no guarantee of consistent service availability.

- [source1](https://support.google.com/drive/thread/167256653?hl=en&msgid=167271626)
- [source2](https://stackoverflow.com/a/10225157)
- [source3](https://mcbltd.atlassian.net/browse/OM-8599?focusedCommentId=2913245)

### Chrome custom tabs

- Custom Tabs are a feature in Android browsers that give app developers a way to add a customized
  browser experience directly within their app.

- Limitations:
    - cannot render PDFs, this too is dependant on the Google Driver URL prefix trick mentioned
      above
    - depends on the assumption that a modern browser is installed on the user’s device
    - if the user is not logged in via a google account on the installed chrome app, the PDF doesn’t
      load even via the Google Drive URL prefix trick
    - redirect URLs do not load out of the box, requiring manual intervention

- [source](https://developer.chrome.com/docs/android/custom-tabs/guide-get-started)

### PdfRenderer

- Android’s native PdfRenderer (API 21+) allows direct rendering of PDF pages into bitmaps.

- Limitations:
    - The class isn’t thread-safe, requiring manual efforts to safely use it
    - URLs within the pages are not clickable since the pages are rendered as static images.

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

### Popular 3rd party libs

- [PSPDFKit](https://pspdfkit.com/pdf-sdk/android)
    - Limitations:
        - incurs costs
        - difficult to customize

- [Barteksc/AndroidPdfViewer](https://github.com/barteksc/AndroidPdfViewerV1)
    - increases app size by around 16MB
    - you cannot open PDF directly using URL. You must download the PDF first, then supply the lib
      that. This requires additional efforts by the developer.
    - no active development in this library

___
