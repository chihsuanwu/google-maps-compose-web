package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.CameraPosition
import com.chihsuanwu.maps.compose.web.LatLng
import js.core.jso

/**
 * A LatLng json object. This is used to pass into constructor of
 * [google.maps.LatLng](https://developers.google.com/maps/documentation/javascript/reference/coordinates#LatLng)
 */
internal external interface LatLngJson {
    var lat: Double
    var lng: Double
}

internal fun LatLng.toLatLngJson(): LatLngJson {
    return jso {
        lat = this@toLatLngJson.lat
        lng = this@toLatLngJson.lng
    }
}

/**
 * A [google.maps.LatLng](https://developers.google.com/maps/documentation/javascript/reference/coordinates#LatLng)
 * object.
 */
internal external interface JsLatLng {
    var lat: () -> Double
    var lng: () -> Double
}

internal fun JsLatLng.toLatLng(): LatLng {
    return LatLng(lat(), lng())
}

/**
 * A [google.maps.CameraOptions](https://developers.google.com/maps/documentation/javascript/reference/map#CameraOptions)
 * object.
 */
internal external interface CameraOptions {
    var center: LatLngJson
    var zoom: Double
    var tilt: Double
    var heading: Double
}

internal fun CameraPosition.toCameraOptions(): CameraOptions {
    return jso {
        center = this@toCameraOptions.center.toLatLngJson()
        zoom = this@toCameraOptions.zoom
        tilt = this@toCameraOptions.tilt
        heading = this@toCameraOptions.heading
    }
}
