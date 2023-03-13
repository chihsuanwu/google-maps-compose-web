package com.chihsuanwu.maps.compose.web.jsobject.utils

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

internal fun List<LatLng>.toLatLngJsonArray(): Array<LatLngJson> {
    return map { it.toLatLngJson() }.toTypedArray()
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
