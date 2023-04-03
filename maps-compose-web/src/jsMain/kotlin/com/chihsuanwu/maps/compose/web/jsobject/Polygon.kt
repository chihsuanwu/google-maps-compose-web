package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.StrokePosition

/**
 * A [google.maps.Polygon](https://developers.google.com/maps/documentation/javascript/reference/polygon#Polygon.constructor) object
 */
internal external interface JsPolygon : AddListener {

    fun setMap(map: MapView?)

    fun setOptions(options: PolygonOptions)
}

internal fun newPolygon(options: PolygonOptions): JsPolygon {
    return js("new google.maps.Polygon(options);") as JsPolygon
}

internal external interface PolygonOptions {
    var clickable: Boolean
    var draggable: Boolean
    var editable: Boolean
    var fillColor: String
    var fillOpacity: Double
    var geodesic: Boolean
    var map: MapView?
    var paths: Array<JsLatLngLiteral>
    var strokeColor: String
    var strokeOpacity: Double
    var strokePosition: dynamic
    var strokeWeight: Int
    var visible: Boolean
    var zIndex: Double?
}

internal fun StrokePosition.toJs(): dynamic {
    return when (this) {
        StrokePosition.CENTER -> js("google.maps.StrokePosition.CENTER")
        StrokePosition.INSIDE -> js("google.maps.StrokePosition.INSIDE")
        StrokePosition.OUTSIDE -> js("google.maps.StrokePosition.OUTSIDE")
    }
}