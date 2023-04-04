package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.IconMouseEvent
import com.chihsuanwu.maps.compose.web.MapMouseEvent
import com.chihsuanwu.maps.compose.web.PolyMouseEvent

/**
 * [google.maps.MapsEventListener](https://developers.google.com/maps/documentation/javascript/reference/event#MapsEventListener)
 */
internal external interface MapsEventListener {
    fun remove()
}

internal external interface AddListener {
    /**
     * Adds the given listener function to the given event name.
     */
    fun addListener(event: String, callback: (dynamic) -> Unit): MapsEventListener
}

/**
 * [google.maps.MapMouseEvent](https://developers.google.com/maps/documentation/javascript/reference/map#MapMouseEvent
 */
internal external interface JsMapMouseEvent {
    val latLng: JsLatLng
}

internal fun JsMapMouseEvent.toMouseEvent(): MapMouseEvent {
    return MapMouseEvent(
        latLng = latLng.toLatLng()
    )
}

/**
 * [google.maps.IconMouseEvent](https://developers.google.com/maps/documentation/javascript/reference/map#IconMouseEvent)
 */
internal external interface JsIconMouseEvent : JsMapMouseEvent {
    val placeId: String
}

internal fun JsIconMouseEvent.toIconMouseEvent(): IconMouseEvent {
    return IconMouseEvent(
        latLng = latLng.toLatLng(),
        placeId = placeId
    )
}

/**
 * [google.maps.PolyMouseEvent](https://developers.google.com/maps/documentation/javascript/reference/polygon#PolyMouseEvent)
 */
internal external interface JsPolyMouseEvent : JsMapMouseEvent {
    val edge: Int
    val path: Int
    val vertex: Int
}

internal fun JsPolyMouseEvent.toPolyMouseEvent(): PolyMouseEvent {
    return PolyMouseEvent(
        latLng = latLng.toLatLng(),
        edge = edge,
        path = path,
        vertex = vertex
    )
}
