package com.chihsuanwu.maps.compose.web.jsobject

/**
 * A [google.maps.Polyline](https://developers.google.com/maps/documentation/javascript/reference/polygon#Polyline.constructor) object
 */
internal external interface JsPolyline {

    fun setMap(map: MapView?)

    fun setOptions(options: PolylineOptions)

}

internal fun newPolyline(options: PolylineOptions): JsPolyline {
    return js("new google.maps.Polyline(options);") as JsPolyline
}

internal external interface PolylineOptions {
    var clickable: Boolean
    var draggable: Boolean
    var editable: Boolean
    var geodesic: Boolean
    // TODO: var icons: Array<IconSequence>
    var map: MapView?
    var path: Array<LatLngJson>
    var strokeColor: String
    var strokeOpacity: Double
    var strokeWeight: Int
    var visible: Boolean
    var zIndex: Double
}