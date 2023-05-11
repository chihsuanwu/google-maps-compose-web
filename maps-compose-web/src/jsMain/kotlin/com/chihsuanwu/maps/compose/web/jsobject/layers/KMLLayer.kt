package com.chihsuanwu.maps.compose.web.jsobject.layers

import com.chihsuanwu.maps.compose.web.KMLMouseEvent
import com.chihsuanwu.maps.compose.web.PolyMouseEvent
import com.chihsuanwu.maps.compose.web.Size
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.AddListener
import com.chihsuanwu.maps.compose.web.jsobject.JsMapMouseEvent
import com.chihsuanwu.maps.compose.web.jsobject.JsPolyMouseEvent
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.toLatLng
import com.chihsuanwu.maps.compose.web.layers.KMLAuthor
import com.chihsuanwu.maps.compose.web.layers.KMLFeatureData

/**
 * [google.maps.KmlLayer](https://developers.google.com/maps/documentation/javascript/reference/kml#KmlLayer)
 */
internal external interface JsKMLLayer : AddListener {
    fun setMap(map: Any?)
    fun setOptions(options: JsKMLLayerOptions)
}

internal fun newKMLLayer(options: JsKMLLayerOptions): JsKMLLayer {
    return js("new google.maps.KmlLayer(options);") as JsKMLLayer
}

internal external interface JsKMLLayerOptions {
    var clickable: Boolean
    var map: MapView?
    var preserveViewport: Boolean
    var screenOverlays: Boolean
    var suppressInfoWindows: Boolean
    var url: String?
    var zIndex: Double?
}

/**
 * [google.maps.KmlMouseEvent](https://developers.google.com/maps/documentation/javascript/reference/kml#KmlMouseEvent)
 */
internal external interface JsKMLMouseEvent : JsMapMouseEvent {
    val featureData: dynamic
    val pixelOffset: JsSize
}

internal fun JsKMLMouseEvent.toKMLMouseEvent(): KMLMouseEvent {
    return KMLMouseEvent(
        featureData = KMLFeatureData(
            author = KMLAuthor(
                email = featureData.author.email as? String,
                name = featureData.author.name as? String,
                uri = featureData.author.uri as? String
            ),
            description = featureData.description as? String ?: "",
            id = featureData.id as? String ?: "",
            infoWindowHtml = featureData.infoWindowHtml as? String ?: "",
            name = featureData.name as? String ?: "",
            snippet = featureData.snippet as? String ?: ""
        ),
        latLng = latLng.toLatLng(),
        pixelOffset = Size(pixelOffset.width, pixelOffset.height)
    )
}