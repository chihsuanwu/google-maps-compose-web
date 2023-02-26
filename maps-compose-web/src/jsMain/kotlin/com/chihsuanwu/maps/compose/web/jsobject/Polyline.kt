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

    var path: Array<LatLngJson>

    var strokeColor: String

    var strokeWeight: Int

}