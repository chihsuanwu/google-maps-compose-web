package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.CameraPosition
import com.chihsuanwu.maps.compose.web.jsobject.utils.JsLatLng
import com.chihsuanwu.maps.compose.web.jsobject.utils.LatLngJson
import com.chihsuanwu.maps.compose.web.jsobject.utils.toLatLngJson
import js.core.jso


/**
 * A [google.maps.Map](https://developers.google.com/maps/documentation/javascript/reference/map) object.
 */
internal external interface MapView {

    /**
     * Returns the position displayed at the center of the map.
     */
    fun getCenter(): JsLatLng

    /**
     * Returns the zoom level of the map.
     */
    fun getZoom(): Double

    /**
     * Immediately sets the map's camera to the target camera options, without animation.
     */
    fun moveCamera(cameraOptions: CameraOptions)

    fun setCenter(center: LatLngJson)

    fun setZoom(zoom: Double)

    /**
     * Adds the given listener function to the given event name.
     */
    fun addListener(eventName: String, handler: () -> Unit)
}

/**
 * Create a [MapView] object.
 *
 * @param id The id of the element to be used as the map container.
 */
internal fun newMap(
    id: String
): MapView {
    return js("new google.maps.Map(document.getElementById(id));") as MapView
}

/**
 * A [google.maps.CameraOptions](https://developers.google.com/maps/documentation/javascript/reference/map#CameraOptions)
 * object.
 */
internal external interface CameraOptions {
    var center: LatLngJson
    var zoom: Double
    var tilt: Double
    var heading: Double
}

internal fun CameraPosition.toCameraOptions(): CameraOptions {
    return jso {
        center = this@toCameraOptions.center.toLatLngJson()
        zoom = this@toCameraOptions.zoom
        tilt = this@toCameraOptions.tilt
        heading = this@toCameraOptions.heading
    }
}