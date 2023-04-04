package com.chihsuanwu.maps.compose.web.jsobject.layers

import com.chihsuanwu.maps.compose.web.jsobject.MapView

/**
 * [google.maps.TrafficLayer](https://developers.google.com/maps/documentation/javascript/reference/map#TrafficLayer)
 */
internal external interface JsTrafficLayer {
    fun setMap(map: MapView?)
    fun setOptions(options: JsTrafficLayerOptions)
}

internal fun newTrafficLayer(options: JsTrafficLayerOptions): JsTrafficLayer {
    return js("new google.maps.TrafficLayer(options);") as JsTrafficLayer
}

internal external interface JsTrafficLayerOptions {
    var autoRefresh: Boolean?
    var map: MapView?
}
