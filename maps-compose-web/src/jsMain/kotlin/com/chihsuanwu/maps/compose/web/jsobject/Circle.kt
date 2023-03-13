package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.jsobject.utils.LatLngJson

/**
 * A [google.maps.Circle](https://developers.google.com/maps/documentation/javascript/reference/polygon#Polygon.constructor) object
 */
internal external interface JsCircle {

    fun setMap(map: MapView?)

    fun setOptions(options: CircleOptions)

    fun addListener(event: String, callback: (Any) -> Unit)
}

internal fun newCircle(options: CircleOptions): JsCircle {
    return js("new google.maps.Circle(options);") as JsCircle
}

internal external interface CircleOptions {
    var center: LatLngJson
    var clickable: Boolean
    var draggable: Boolean
    var editable: Boolean
    var fillColor: String
    var fillOpacity: Double
    var map: MapView?
    var radius: Double
    var strokeColor: String
    var strokeOpacity: Double
    var strokePosition: dynamic
    var strokeWeight: Int
    var visible: Boolean
    var zIndex: Double?
}
