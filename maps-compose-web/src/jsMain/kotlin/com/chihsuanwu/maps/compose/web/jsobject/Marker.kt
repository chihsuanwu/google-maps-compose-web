package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.jsobject.utils.JsLatLng
import com.chihsuanwu.maps.compose.web.jsobject.utils.LatLngJson
import com.chihsuanwu.maps.compose.web.jsobject.utils.PointJson



/**
 * A [google.maps.Marker](https://developers.google.com/maps/documentation/javascript/reference/marker#Marker.constructor) object
 */
internal external interface JsMarker : AddListener {

    fun setMap(map: MapView?)

    fun setOptions(options: MarkerOptions)

//    fun addListener(event: String, callback: (Any) -> Unit)

    fun getMap(): MapView

    fun getPosition(): JsLatLng
}

internal fun newMarker(options: MarkerOptions): JsMarker {
    return js("new google.maps.Marker(options);") as JsMarker
}

internal external interface MarkerOptions {
    var anchorPoint: PointJson?
    var animation: dynamic
    var clickable: Boolean
    var crossOnDrag: Boolean
    var cursor: String?
    var draggable: Boolean
    var icon: String?
    var label: String?
    var map: MapView?
    var opacity: Double
    var optimized: Boolean?
    var position: LatLngJson
    // TODO: var shape: MarkerShape
    var title: String?
    var visible: Boolean
    var zIndex: Double?
}