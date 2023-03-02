package com.chihsuanwu.maps.compose.web.jsobject

/**
 * A [google.maps.Marker](https://developers.google.com/maps/documentation/javascript/reference/marker#Marker.constructor) object
 */
internal external interface JsMarker {

    fun setMap(map: MapView?)

    fun setOptions(options: MarkerOptions)

}

internal fun newMarker(options: MarkerOptions): JsMarker {
    return js("new google.maps.Marker(options);") as JsMarker
}

internal external interface MarkerOptions {

    var position: LatLngJson

    var icon: String?

    var title: String?

}