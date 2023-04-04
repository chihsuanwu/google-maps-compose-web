package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.IconSequence
import js.core.jso

/**
 * A [google.maps.Polyline](https://developers.google.com/maps/documentation/javascript/reference/polygon#Polyline.constructor) object
 */
internal external interface JsPolyline : AddListener {

    fun setMap(map: MapView?)

    fun setOptions(options: PolylineOptions)
}

internal fun newPolyline(options: PolylineOptions): JsPolyline {
    return js("new google.maps.Polyline(options);") as JsPolyline
}

/**
 * [google.maps.PolylineOptions](https://developers.google.com/maps/documentation/javascript/reference/polygon#PolylineOptions)
 */
internal external interface PolylineOptions {
    var clickable: Boolean
    var draggable: Boolean
    var editable: Boolean
    var geodesic: Boolean
    var icons: Array<JsIconSequence>?
    var map: MapView?
    var path: Array<JsLatLngLiteral>
    var strokeColor: String
    var strokeOpacity: Double
    var strokeWeight: Int
    var visible: Boolean
    var zIndex: Double?
}

/**
 * [google.maps.IconSequence](https://developers.google.com/maps/documentation/javascript/reference/polygon#IconSequence)
 */
internal external interface JsIconSequence {
    var fixedRotation: Boolean?
    var icon: JsSymbol?
    var offset: String?
    var repeat: String?
}

internal fun IconSequence.toJsIconSequence(): JsIconSequence {
    val sequence = this
    return jso {
        fixedRotation = sequence.fixedRotation
        icon = sequence.icon?.toJsSymbol()
        offset = sequence.offset
        repeat = sequence.repeat
    }
}

internal fun List<IconSequence>.toJsIconSequenceArray(): Array<JsIconSequence> {
    return map { it.toJsIconSequence() }.toTypedArray()
}