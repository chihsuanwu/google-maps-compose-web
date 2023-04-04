package com.chihsuanwu.maps.compose.web.jsobject.layers

import com.chihsuanwu.maps.compose.web.jsobject.MapView

/**
 * [google.maps.BicyclingLayer](https://developers.google.com/maps/documentation/javascript/reference/map#BicyclingLayer)
 */
internal external interface JsBicyclingLayer {
    fun setMap(map: MapView?)
}

internal fun newBicyclingLayer(): JsBicyclingLayer {
    return js("new google.maps.BicyclingLayer();") as JsBicyclingLayer
}
