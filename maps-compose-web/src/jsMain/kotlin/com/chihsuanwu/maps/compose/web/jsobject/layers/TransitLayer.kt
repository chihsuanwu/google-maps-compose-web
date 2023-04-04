package com.chihsuanwu.maps.compose.web.jsobject.layers

import com.chihsuanwu.maps.compose.web.jsobject.MapView

/**
 * [google.maps.TransitLayer](https://developers.google.com/maps/documentation/javascript/reference/map#TransitLayer)
 */
internal external interface JsTransitLayer {
    fun setMap(map: MapView?)
}

internal fun newTransitLayer(): JsTransitLayer {
    return js("new google.maps.TransitLayer();") as JsTransitLayer
}
