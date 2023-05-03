package com.chihsuanwu.maps.compose.web.jsobject.layers

import com.chihsuanwu.maps.compose.web.jsobject.MapView

/**
 * [google.maps.KmlLayer](https://developers.google.com/maps/documentation/javascript/reference/kml#KmlLayer)
 */
internal external interface JsKMLLayer {
    fun setMap(map: Any?)
    fun setOptions(options: JsKMLLayerOptions)
}

internal fun newKMLLayer(options: JsKMLLayerOptions): JsKMLLayer {
    return js("new google.maps.KmlLayer(options);") as JsKMLLayer
}

internal external interface JsKMLLayerOptions {
    var clickable: Boolean
    var map: MapView?
    var preserveViewport: Boolean
    var screenOverlays: Boolean
    var suppressInfoWindows: Boolean
    var url: String?
    var zIndex: Double?
}
