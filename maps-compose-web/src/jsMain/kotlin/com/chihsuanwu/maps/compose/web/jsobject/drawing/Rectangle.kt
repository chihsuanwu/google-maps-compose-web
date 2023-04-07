package com.chihsuanwu.maps.compose.web.jsobject.drawing

import com.chihsuanwu.maps.compose.web.jsobject.AddListener
import com.chihsuanwu.maps.compose.web.jsobject.JsLatLngBoundsLiteral
import com.chihsuanwu.maps.compose.web.jsobject.MapView

/**
 * A [google.maps.Rectangle](https://developers.google.com/maps/documentation/javascript/reference/polygon#Rectangle) object.
 */
internal external interface JsRectangle : AddListener {

    fun setMap(map: MapView?)

    fun setOptions(options: RectangleOptions)
}

internal fun newRectangle(options: RectangleOptions): JsRectangle {
    return js("new google.maps.Rectangle(options);") as JsRectangle
}

internal external interface RectangleOptions {
    var bounds: JsLatLngBoundsLiteral
    var clickable: Boolean
    var draggable: Boolean
    var editable: Boolean
    var fillColor: String
    var fillOpacity: Double
    var map: MapView?
    var strokeColor: String
    var strokeOpacity: Double
    var strokePosition: dynamic
    var strokeWeight: Int
    var visible: Boolean
    var zIndex: Double?
}
