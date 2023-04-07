package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.LatLng
import com.chihsuanwu.maps.compose.web.LatLngBounds
import com.chihsuanwu.maps.compose.web.Point
import com.chihsuanwu.maps.compose.web.Size
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

internal fun LatLngBounds.toJsLatLngBoundsLiteral(): JsLatLngBoundsLiteral {
    return jso {
        east = this@toJsLatLngBoundsLiteral.east
        north = this@toJsLatLngBoundsLiteral.north
        south = this@toJsLatLngBoundsLiteral.south
        west = this@toJsLatLngBoundsLiteral.west
    }
}

/**
 * A [google.maps.Point](https://developers.google.com/maps/documentation/javascript/reference/coordinates#Point)
 */
internal external interface JsPoint {
    var x: Double
    var y: Double
}

internal fun Point.toJsPoint(): JsPoint {
    return jso {
        x = this@toJsPoint.x
        y = this@toJsPoint.y
    }
}

/**
 * A [google.maps.Size](https://developers.google.com/maps/documentation/javascript/reference/coordinates#Size)
 */
internal external interface JsSize {
    var height: Double
    var width: Double
}

internal fun Size.toJsSize(): JsSize {
    return jso {
        height = this@toJsSize.height
        width = this@toJsSize.width
    }
}
