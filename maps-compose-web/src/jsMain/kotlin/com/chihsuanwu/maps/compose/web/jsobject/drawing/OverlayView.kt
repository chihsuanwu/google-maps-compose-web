package com.chihsuanwu.maps.compose.web.jsobject.drawing

import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.JsLatLng
import com.chihsuanwu.maps.compose.web.jsobject.JsPoint
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import org.w3c.dom.Element

internal external interface JSOverlayView {
//    fun onAdd()
//    fun draw()
//    fun onRemove()

    fun setMap(map: MapView?)
    fun getPanes(): JSMapPanes
    fun getProjection(): JSProjection
}

internal fun newOverlayView(
    onAdd: () -> Unit,
    draw: () -> Unit,
    onRemove: () -> Unit
): JSOverlayView {
    val view = js("new google.maps.OverlayView();")
    view.onAdd = onAdd
    view.draw = draw
    view.onRemove = onRemove

    return view as JSOverlayView
}

internal external interface JSMapPanes {
    var floatPane: Element
    var mapPane: Element
    var markerLayer: Element
    var overlayLayer: Element
    var overlayMouseTarget: Element
}

internal external interface JSProjection {
    fun fromContainerPixelToLatLng(pixel: JsPoint): JsLatLng
    fun fromDivPixelToLatLng(pixel: JsPoint): JsLatLng
    fun fromLatLngToContainerPixel(latLng: JsLatLngLiteral): JsPoint
    fun fromLatLngToDivPixel(latLng: JsLatLngLiteral): JsPoint
}