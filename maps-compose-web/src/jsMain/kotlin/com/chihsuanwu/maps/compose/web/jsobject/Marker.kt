package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.MarkerAnimation
import com.chihsuanwu.maps.compose.web.MarkerIcon
import com.chihsuanwu.maps.compose.web.MarkerLabel
import com.chihsuanwu.maps.compose.web.MarkerShape
import js.core.jso


/**
 * A [google.maps.Marker](https://developers.google.com/maps/documentation/javascript/reference/marker#Marker.constructor) object
 */
internal external interface JsMarker : JsLatLng, AddListener {

    fun setMap(map: MapView?)
    fun setOptions(options: MarkerOptions)
    fun getMap(): MapView
    fun getPosition(): JsLatLng
}

internal fun newMarker(options: MarkerOptions): JsMarker {
    return js("new google.maps.Marker(options);") as JsMarker
}

/**
 * [google.maps.MarkerOptions](https://developers.google.com/maps/documentation/javascript/reference/marker#MarkerOptions)
 */
internal external interface MarkerOptions {
    var anchorPoint: JsPoint?
    var animation: JsAnimation?
    var clickable: Boolean
    var crossOnDrag: Boolean
    var cursor: String?
    var draggable: Boolean
    var icon: JsMarkerIcon?
    var label: JsMarkerLabel?
    var map: MapView?
    var opacity: Double
    var optimized: Boolean?
    var position: JsLatLngLiteral
    var shape: JsMarkerShape?
    var title: String?
    var visible: Boolean
    var zIndex: Double?
}

internal external interface JsAnimation

internal fun MarkerAnimation.toJsAnimation(): JsAnimation {
    return when (this) {
        MarkerAnimation.DROP -> js("google.maps.Animation.DROP") as JsAnimation
        MarkerAnimation.BOUNCE -> js("google.maps.Animation.BOUNCE") as JsAnimation
    }
}

internal external interface JsMarkerIcon

internal external interface JsURLIcon : JsMarkerIcon

internal external interface JsIcon : JsMarkerIcon {
    var url: String
    var anchor: JsPoint?
    var labelOrigin: JsPoint?
    var origin: JsPoint?
    var scaledSize: JsSize?
    var size: JsSize?
}

internal external interface JsSymbol : JsMarkerIcon {
    var path: String
    var anchor: JsPoint?
    var fillColor: String?
    var fillOpacity: Double?
    var labelOrigin: JsPoint?
    var rotation: Double?
    var scale: Double?
    var strokeColor: String?
    var strokeOpacity: Double?
    var strokeWeight: Double?
}

internal fun MarkerIcon.Icon.toJsIcon(): JsIcon {
    val icon = this
    return jso {
        url = icon.url
        anchor = icon.anchor?.toJsPoint()
        labelOrigin = icon.labelOrigin?.toJsPoint()
        origin = icon.origin?.toJsPoint()
        scaledSize = icon.scaledSize?.toJsSize()
        size = icon.size?.toJsSize()
    }
}

internal fun MarkerIcon.Symbol.toJsSymbol(): JsSymbol {
    val symbol = this
    return jso {
        path = symbol.path
        anchor = symbol.anchor?.toJsPoint()
        fillColor = symbol.fillColor
        fillOpacity = symbol.fillOpacity
        labelOrigin = symbol.labelOrigin?.toJsPoint()
        rotation = symbol.rotation
        scale = symbol.scale
        strokeColor = symbol.strokeColor
        strokeOpacity = symbol.strokeOpacity
        strokeWeight = symbol.strokeWeight
    }
}

internal fun MarkerIcon.toJsMarkerIcon(): JsMarkerIcon {
    return when (this) {
        is MarkerIcon.URL -> this.url as JsURLIcon
        is MarkerIcon.Icon -> toJsIcon()
        is MarkerIcon.Symbol -> toJsSymbol()
    }
}

internal external interface JsMarkerLabel

internal external interface JsText : JsMarkerLabel

internal external interface JsLabel : JsMarkerLabel {
    var text: String
    var className: String?
    var color: String?
    var fontFamily: String?
    var fontSize: String?
    var fontWeight: String?
}

internal fun MarkerLabel.Label.toJsLabel(): JsLabel {
    val label = this
    return jso {
        text = label.text
        className = label.className
        color = label.color
        fontFamily = label.fontFamily
        fontSize = label.fontSize
        fontWeight = label.fontWeight
    }
}

internal fun MarkerLabel.toJsMarkerLabel(): JsMarkerLabel {
    return when (this) {
        is MarkerLabel.Text -> this.text as JsText
        is MarkerLabel.Label -> toJsLabel()
    }
}

internal external interface JsMarkerShape {
    var coords: Array<Int>
    var type: String
}

internal fun MarkerShape.toJsMarkerShape(): JsMarkerShape {
    return jso {
        coords = this@toJsMarkerShape.coords.toTypedArray()
        type = when(this@toJsMarkerShape.type) {
            MarkerShape.Type.Circle -> "circle"
            MarkerShape.Type.Poly -> "poly"
            MarkerShape.Type.Rect -> "rect"
        }
    }
}