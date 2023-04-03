package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.*
import js.core.jso


/**
 * A [google.maps.Map](https://developers.google.com/maps/documentation/javascript/reference/map) object.
 */
internal external interface MapView : AddListener {

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

    fun setCenter(center: JsLatLngLiteral)

    fun setOptions(options: JsMapOptions)

    fun setZoom(zoom: Double)
}

/**
 * Create a [MapView] object.
 *
 * @param id The id of the element to be used as the map container.
 */
internal fun newMap(
    id: String,
    options: JsMapOptions,
): MapView {
    return js("new google.maps.Map(document.getElementById(id), options);") as MapView
}


/**
 * A [google.maps.CameraOptions](https://developers.google.com/maps/documentation/javascript/reference/map#CameraOptions)
 * object.
 */
internal external interface CameraOptions {
    var center: JsLatLngLiteral
    var zoom: Double
    var tilt: Double
    var heading: Double
}

internal fun CameraPosition.toCameraOptions(): CameraOptions {
    return jso {
        center = this@toCameraOptions.center.toJsLatLngLiteral()
        zoom = this@toCameraOptions.zoom
        tilt = this@toCameraOptions.tilt
        heading = this@toCameraOptions.heading
    }
}