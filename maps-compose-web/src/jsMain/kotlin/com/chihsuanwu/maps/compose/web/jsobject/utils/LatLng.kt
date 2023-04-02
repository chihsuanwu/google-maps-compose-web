package com.chihsuanwu.maps.compose.web.jsobject.utils

import com.chihsuanwu.maps.compose.web.LatLng
import com.chihsuanwu.maps.compose.web.LatLngBounds
import js.core.jso

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
 * A [google.maps.LatLngLiteral](https://developers.google.com/maps/documentation/javascript/reference/coordinates#LatLngLiteral)
 * object.
 */
internal external interface JsLatLngLiteral {
    var lat: Double
    var lng: Double
}

internal fun LatLng.toJsLatLngLiteral(): JsLatLngLiteral {
    return jso {
        lat = this@toJsLatLngLiteral.lat
        lng = this@toJsLatLngLiteral.lng
    }
}

internal fun List<LatLng>.toJsLatLngLiteralArray(): Array<JsLatLngLiteral> {
    return map { it.toJsLatLngLiteral() }.toTypedArray()
}

/**
 * A [google.maps.LatLngBounds](https://developers.google.com/maps/documentation/javascript/reference/coordinates#LatLngBounds)
 * object.
 */
internal external interface JsLatLngBoundsLiteral {
    var east: Double
    var north: Double
    var south: Double
    var west: Double
}

internal fun LatLngBounds.toJsLatLngBounds(): JsLatLngBoundsLiteral {
    return jso {
        east = this@toJsLatLngBounds.east
        north = this@toJsLatLngBounds.north
        south = this@toJsLatLngBounds.south
        west = this@toJsLatLngBounds.west
    }
}