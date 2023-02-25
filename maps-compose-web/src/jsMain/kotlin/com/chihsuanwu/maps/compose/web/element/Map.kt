package com.chihsuanwu.maps.compose.web.element


/**
 * A [google.maps.Map](https://developers.google.com/maps/documentation/javascript/reference/map) object.
 */
external interface GoogleMap {

    /**
     * Returns the position displayed at the center of the map.
     * See [getCenter](https://developers.google.com/maps/documentation/javascript/reference/map#Map.getCenter)
     * for more details.
     */
    fun getCenter(): JsLatLng

    /**
     * Returns the zoom level of the map.
     * See [getZoom](https://developers.google.com/maps/documentation/javascript/reference/map#Map.getZoom)
     * for more details.
     */
    fun getZoom(): Double

    /**
     * Immediately sets the map's camera to the target camera options, without animation.
     * See [moveCamera](https://developers.google.com/maps/documentation/javascript/reference/map#Map.moveCamera)
     * for more details.
     */
    fun moveCamera(cameraOptions: CameraOptions)

    fun setCenter(center: LatLngJson)

    fun setZoom(zoom: Double)

    /**
     * Adds the given listener function to the given event name.
     * See [addListener](https://developers.google.com/maps/documentation/javascript/reference/event#MVCObject.addListener)
     * for more details.
     */
    fun addListener(eventName: String, handler: () -> Unit)
}

/**
 * Create a [GoogleMap] object.
 *
 * @param id The id of the element to be used as the map container.
 * @param center The initial center of the map.
 * @param zoom The initial zoom level of the map.
 */
internal fun googleMap(
    id: String,
    center: LatLngJson,
    zoom: Double,
): GoogleMap {
    return js(
        """
            new google.maps.Map(document.getElementById(id), {
                center: center,
                zoom: zoom
            });
        """
    ) as GoogleMap
}