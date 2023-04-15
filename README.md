# Google Maps Compose Web

[![](https://jitpack.io/v/chihsuanwu/google-maps-compose-web.svg)](https://jitpack.io/#chihsuanwu/google-maps-compose-web)

A library for using Google Maps in [Compose HTML](https://github.com/JetBrains/compose-jb).

This library is inspired by [Maps Compose for Android](https://github.com/googlemaps/android-maps-compose).

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
            width(100.percent)
            height(100.percent)
        }
    }
)
```

<details>
  <summary>Configuring the map</summary>

## Configuring the map

Configuring the map can be done by passing a `MapOptions` object to the `GoogleMap` composable.
    
```kotlin
val mapOptions = remember {
    MapOptions(
        fullscreenControl = false,
        // ...
    )
}

GoogleMap(
    // ...
    mapOptions = mapOptions,
) {
    // ...
}
```

</details>

<details>
  <summary>Handling map events</summary>

## Handling map events

Map events can be handled by passing a lambda expression to the `GoogleMap` composable.

```kotlin
GoogleMap(
    // ...
    onClick = {
        console.log("Map clicked!")
    },
    onDrag = {
        console.log("Map dragged!")
    },
    // Add more events here
) {
    // ...
}
```

</details>

<details>
  <summary>Drawing on the map</summary>

## Drawing on the map

Adding child composable, such as `Marker`, to the `GoogleMap` composable.

```kotlin
GoogleMap(
    // ...
) {
    Marker(
        state = MarkerState(position = LatLng(23.2, 120.5)),
        onClick = {
            console.log("Marker clicked!")
        },
        // ...
    )
}
```

Currently, the following drawing composable are supported:
- `Marker`
- `Polyline`
- `Polygon`
- `Circle`
- `InfoWindow`

</details>

<details>
  <summary>Marker's Info Window</summary>

## Marker's Info Window

An info window can be added to a `Marker` directly by passing a lambda expression to the `infoContent` parameter.

To show the info window, call `showInfoWindow()` on the `MarkerState`.

```kotlin
state = rememberMarkerState()

Marker(
    state = state,
    // ...
    infoContent = {
        Div {
            Span({ style { fontSize(20.px) } }) {
                Text("Info Window Title")
            }
            Text("Info Window Content")
        }
    }
) 

// show the info window
state.showInfoWindow()
```

</details>

<details>
  <summary>Map Layers</summary>

## Map Layers

Map layers can be added to the `GoogleMap` composable.

```kotlin
GoogleMap(
    // ...
) {
    if (showTrafficLayer) {
        TrafficLayer()
    }
}
```

Currently, `TrafficLayer`, `TransitLayer`, and `BicyclingLayer` are supported.

</details>

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

# Current State

**This library is currently in alpha state and the API is subject to change.** 

There are still many advanced features that are not yet supported.
However, if you are a user of Compose HTML and would like to use Google Maps in your web application, 
this library is still worth a try.

Feedback and contributions are highly appreciated! Feel free to open an issue or submit a pull request.

If you like this library, please consider starring this project, so we know that it is useful to you.
