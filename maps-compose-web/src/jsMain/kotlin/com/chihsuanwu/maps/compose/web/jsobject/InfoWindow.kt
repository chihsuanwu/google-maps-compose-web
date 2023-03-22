package com.chihsuanwu.maps.compose.web.jsobject

import org.w3c.dom.Element

internal external interface InfoWindow : AddListener {
    fun setContent(string: String)
    fun setContent(content: Element)
    fun open(options: InfoWindowOpenOptions)
    fun close()
}

internal fun newInfoWindow(): InfoWindow {
    return js("new google.maps.InfoWindow();") as InfoWindow
}

internal external interface InfoWindowOpenOptions {
    var anchor: JsMarker
    var map: MapView
}