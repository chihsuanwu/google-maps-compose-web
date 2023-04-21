package com.chihsuanwu.maps.compose.web.jsobject.drawing

import com.chihsuanwu.maps.compose.web.drawing.MapPanes
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.JsLatLng
import com.chihsuanwu.maps.compose.web.jsobject.JsPoint
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import org.w3c.dom.Element

internal external interface JSOverlayView {
    fun setMap(map: MapView?)
    fun getPanes(): JSMapPanes
    fun getProjection(): JSProjection

    var onAdd: () -> Unit
    var draw: () -> Unit
    var onRemove: () -> Unit
}

internal fun newOverlayView(): JSOverlayView {
    return js("new google.maps.OverlayView();") as JSOverlayView
}

internal external interface JSMapPanes {
    var floatPane: Element
    var mapPane: Element
    var markerLayer: Element
    var overlayLayer: Element
    var overlayMouseTarget: Element
}

internal fun JSMapPanes.getPane(pane: MapPanes): Element {
    return when (pane) {
        MapPanes.FloatPane -> floatPane
        MapPanes.MapPane -> mapPane
        MapPanes.MarkerLayer -> markerLayer
        MapPanes.OverlayLayer -> overlayLayer
        MapPanes.OverlayMouseTarget -> overlayMouseTarget
    }
}

internal external interface JSProjection {
    fun fromContainerPixelToLatLng(pixel: JsPoint): JsLatLng
    fun fromDivPixelToLatLng(pixel: JsPoint): JsLatLng
    fun fromLatLngToContainerPixel(latLng: JsLatLngLiteral): JsPoint
    fun fromLatLngToDivPixel(latLng: JsLatLngLiteral): JsPoint
}