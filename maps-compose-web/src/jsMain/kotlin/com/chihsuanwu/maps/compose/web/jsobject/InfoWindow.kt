package com.chihsuanwu.maps.compose.web.jsobject

import org.w3c.dom.Element

internal external interface JsInfoWindow : AddListener {
    fun setContent(content: Element)
    fun setOptions(options: InfoWindowOptions)
    fun open(options: InfoWindowOpenOptions)
    fun close()
}

internal fun newInfoWindow(options: InfoWindowOptions): JsInfoWindow {
    return js("new google.maps.InfoWindow(options);") as JsInfoWindow
}

internal external interface InfoWindowOptions {
    var ariaLabel: String?
    var disableAutoPan: Boolean
    var maxWidth: Int?
    var minWidth: Int?
    var pixelOffset: JsSize?
    var position: JsLatLngLiteral
    var zIndex: Double?
}

internal external interface InfoWindowOpenOptions {
    var anchor: JsLatLng?
    var map: MapView?
}