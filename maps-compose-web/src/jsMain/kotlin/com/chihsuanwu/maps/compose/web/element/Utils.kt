package com.chihsuanwu.maps.compose.web.element

import com.chihsuanwu.maps.compose.web.state.CameraPosition
import com.chihsuanwu.maps.compose.web.state.LatLng
import js.core.jso

/**
 * A LatLng json object. This is used to pass into constructor of
 * [google.maps.LatLng](https://developers.google.com/maps/documentation/javascript/reference/coordinates#LatLng)
 */
external interface LatLngJson {
    var lat: Double
    var lng: Double
}

fun LatLng.toLatLngJson(): LatLngJson {
    return jso {
        lat = this@toLatLngJson.lat
        lng = this@toLatLngJson.lng
    }
}

/**
 * A [google.maps.LatLng](https://developers.google.com/maps/documentation/javascript/reference/coordinates#LatLng)
 * object.
 */
external interface JsLatLng {
    var lat: () -> Double
    var lng: () -> Double
}

fun JsLatLng.toLatLng(): LatLng {
    return LatLng(lat(), lng())
}

/**
 * A [google.maps.CameraOptions](https://developers.google.com/maps/documentation/javascript/reference/map#CameraOptions)
 * object.
 */
external interface CameraOptions {
    var center: LatLngJson
    var zoom: Double
    var tilt: Double
    var heading: Double
}

fun CameraPosition.toCameraOptions(): CameraOptions {
    return jso {
        center = this@toCameraOptions.center.toLatLngJson()
        zoom = this@toCameraOptions.zoom
        tilt = this@toCameraOptions.tilt
        heading = this@toCameraOptions.heading
    }
}
