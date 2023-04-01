package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.jsobject.utils.JsLatLng
import com.chihsuanwu.maps.compose.web.jsobject.utils.toLatLng

internal external interface MapsEventListener {
    fun remove()
}

internal external interface AddListener {
    /**
     * Adds the given listener function to the given event name.
     */
    fun addListener(event: String, callback: (dynamic) -> Unit): MapsEventListener
}

internal external interface MapMouseEvent {
    val latLng: JsLatLng
}

internal fun MapMouseEvent.toMouseEvent(): com.chihsuanwu.maps.compose.web.MouseEvent {
    return com.chihsuanwu.maps.compose.web.MouseEvent(
        latLng = latLng.toLatLng()
    )
}