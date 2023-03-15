package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.CameraPosition
import com.chihsuanwu.maps.compose.web.MapOptions
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

    fun setOptions(options: JsMapOptions)

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
    id: String,
    options: JsMapOptions,
): MapView {
    return js("new google.maps.Map(document.getElementById(id), options);") as MapView
}

/**
 * A [google.maps.MapOptions](https://developers.google.com/maps/documentation/javascript/reference/map#MapOptions)
 * object.
 *
 * Some properties that are duplicated in other controller classes are omitted here.
 */
internal external interface JsMapOptions {
    var backgroundColor: String
    var clickableIcons: Boolean
    var controlSize: Int
    var disableDefaultUI: Boolean
    var disableDoubleClickZoom: Boolean
    var draggableCursor: String
    var draggingCursor: String
    var fullscreenControl: Boolean
    var fullscreenControlOptions: dynamic
    var gestureHandling: String
    var isFractionalZoomEnabled: Boolean
    var keyboardShortcuts: Boolean
    var mapId: String
    var mapTypeControl: Boolean
    var mapTypeControlOptions: dynamic
    var mapTypeId: String
    var maxZoom: Double
    var minZoom: Double
    var noClear: Boolean
    var panControl: Boolean
    var panControlOptions: dynamic
    var restriction: dynamic
    var rotateControl: Boolean
    var rotateControlOptions: dynamic
    var scaleControl: Boolean
    var scaleControlOptions: dynamic
    var scrollwheel: Boolean
    var streetView: dynamic
    var streetViewControl: Boolean
    var streetViewControlOptions: dynamic
    var styles: Array<dynamic>
    var zoomControl: Boolean
    var zoomControlOptions: dynamic
}

internal fun MapOptions.toJsMapOptions(): JsMapOptions {
    val opt = this
    return jso {
        opt.backgroundColor?.let { backgroundColor = it }
        opt.clickableIcons?.let { clickableIcons = it }
        opt.controlSize?.let { controlSize = it }
        opt.disableDefaultUI?.let { disableDefaultUI = it }
        opt.disableDoubleClickZoom?.let { disableDoubleClickZoom = it }
        opt.draggableCursor?.let { draggableCursor = it }
        opt.draggingCursor?.let { draggingCursor = it }
        opt.fullscreenControl?.let { fullscreenControl = it }
        fullscreenControlOptions = opt.fullscreenControlOptions
        opt.gestureHandling?.let { gestureHandling = it }
        opt.isFractionalZoomEnabled?.let { isFractionalZoomEnabled = it }
        opt.keyboardShortcuts?.let { keyboardShortcuts = it }
        opt.mapId?.let { mapId = it }
        opt.mapTypeControl?.let { mapTypeControl = it }
        mapTypeControlOptions = opt.mapTypeControlOptions
        opt.mapTypeId?.let { mapTypeId = it }
        opt.maxZoom?.let { maxZoom = it }
        opt.minZoom?.let { minZoom = it }
        opt.noClear?.let { noClear = it }
        opt.panControl?.let { panControl = it }
        panControlOptions = opt.panControlOptions
        restriction = opt.restriction
        opt.rotateControl?.let { rotateControl = it }
        rotateControlOptions = opt.rotateControlOptions
        opt.scaleControl?.let { scaleControl = it }
        scaleControlOptions = opt.scaleControlOptions
        opt.scrollwheel?.let { scrollwheel = it }
        streetView = opt.streetView
        opt.streetViewControl?.let { streetViewControl = it }
        streetViewControlOptions = opt.streetViewControlOptions
        opt.styles?.let { styles = it }
        opt.zoomControl?.let { zoomControl = it }
        zoomControlOptions = opt.zoomControlOptions
    }
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