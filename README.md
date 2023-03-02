# Google Maps Compose Web

[![](https://jitpack.io/v/chihsuanwu/google-maps-compose-web.svg)](https://jitpack.io/#chihsuanwu/google-maps-compose-web)

A library for using Google Maps in [Compose for Web](https://github.com/JetBrains/compose-jb).

This library is heavily inspired by [Maps Compose for Android](https://github.com/googlemaps/android-maps-compose).

**Note that this library is still in development and is not yet ready for production use.**

# Usage

Adding a `GoogleMap` to your Compose UI as follows:

```kotlin
val cameraPositionState = rememberCameraPositionState {
    position = CameraPosition(
        center = LatLng(23.2, 120.5),
        zoom = 8.0,
    )
}
GoogleMap(
    apiKey = "YOUR_API_KEY",
    cameraPositionState = cameraPositionState,
    attrs = {
        style {
            width(500.px)
            height(500.px)
        }
    }
)
```

## Drawing on the map

Adding child composables to the `GoogleMap` content.

**NOTE** currently, only `Marker`, `Polyline`, and `Polygon` are supported, and only limited attributes are supported.

```kotlin
GoogleMap(
    // ...
) {
    Marker(
        state = MarkerState(position = LatLng(23.2, 120.5)),
    )
}
```

# Setup

Add the following to your `build.gradle.kts` file:

```kotlin
repositories {
    maven("https://jitpack.io")
}

kotlin {
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation("com.github.chihsuanwu:google-maps-compose-web:<version>")
            }
        }
    }
}
```

# Contributing

Contributions are highly appreciated! Please create a feature/bugfix branch on 
your own fork and submit a pull request.