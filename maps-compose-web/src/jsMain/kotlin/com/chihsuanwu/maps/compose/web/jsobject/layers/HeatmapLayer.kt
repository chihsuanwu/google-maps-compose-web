package com.chihsuanwu.maps.compose.web.jsobject.layers

import com.chihsuanwu.maps.compose.web.jsobject.JsLatLng
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.toJsLatLng
import com.chihsuanwu.maps.compose.web.layers.WeightedLocation
import js.core.jso

/**
 * [google.maps.visualization.HeatmapLayer](https://developers.google.com/maps/documentation/javascript/reference/visualization#HeatmapLayer)
 */
internal external interface JsHeatmapLayer {
    fun setMap(map: Any?)
    fun setOptions(options: JsHeatmapLayerOptions)
}

internal fun newHeatmapLayer(options: JsHeatmapLayerOptions): JsHeatmapLayer {
    return js("new google.maps.visualization.HeatmapLayer(options);") as JsHeatmapLayer
}

internal external interface JsHeatmapLayerOptions {
    var data: Array<JsWeightedLocation>
    var dissipating: Boolean
    var gradient: Array<String>?
    var map: MapView?
    var maxIntensity: Int?
    var opacity: Double
    var radius: Int?
}

internal external interface JsWeightedLocation {
    var location: JsLatLng
    var weight: Number
}

internal fun WeightedLocation.toJsWeightedLocation(): JsWeightedLocation {
    return jso {
        location = this@toJsWeightedLocation.location.toJsLatLng()
        weight = this@toJsWeightedLocation.weight
    }
}