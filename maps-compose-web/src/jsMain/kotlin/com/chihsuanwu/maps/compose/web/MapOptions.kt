package com.chihsuanwu.maps.compose.web

/**
 * Data class for configuring [GoogleMap] options
 *
 * See [MapOptions](https://developers.google.com/maps/documentation/javascript/reference/map#MapOptions)
 * for more details
 *
 * Note that some attributes that are duplicated in other controllers are not included here
 */
data class MapOptions(
    val backgroundColor: String? = null,
    val clickableIcons: Boolean? = null,
    val controlSize: Int? = null,
    val disableDefaultUI: Boolean? = null,
    val disableDoubleClickZoom: Boolean? = null,
    val draggableCursor: String? = null,
    val draggingCursor: String? = null,
    val fullscreenControl: Boolean? = null,
    val fullscreenControlOptions: FullscreenControlOptions? = null,
    val gestureHandling: String? = null,
    val isFractionalZoomEnabled: Boolean? = null,
    val keyboardShortcuts: Boolean? = null,
    val mapId: String? = null,
    val mapTypeControl: Boolean? = null,
    val mapTypeControlOptions: MapTypeControlOptions? = null,
    val mapTypeId: MapTypeId? = null,
    val maxZoom: Double? = null,
    val minZoom: Double? = null,
    val noClear: Boolean? = null,
    val panControl: Boolean? = null,
    val panControlOptions: PanControlOptions? = null,
    val restriction: MapRestriction? = null,
    val rotateControl: Boolean? = null,
    val rotateControlOptions: RotateControlOptions? = null,
    val scaleControl: Boolean? = null,
    val scaleControlOptions: dynamic = null,
    val scrollwheel: Boolean? = null,
    val streetView: dynamic = null,
    val streetViewControl: Boolean? = null,
    val streetViewControlOptions: StreetViewControlOptions? = null,
    val styles: MapTypeStyles? = null,
    val zoomControl: Boolean? = null,
    val zoomControlOptions: ZoomControlOptions? = null,
)

/**
 * Wrapper class for [MapOptions.styles] to allow for easier JSON parsing.
 *
 * See [MapTypeStyle](https://developers.google.com/maps/documentation/javascript/reference/map#MapTypeStyle)
 * for more details
 */
data class MapTypeStyles(
    internal val styles: Array<MapTypeStyle>,
) {
    companion object {
        /**
         * Create a [MapTypeStyles] from a JSON string.
         */
        fun fromString(jsonString: String): MapTypeStyles {
            val array = js("JSON.parse(jsonString)")
            return (array as Array<dynamic>).map {
                MapTypeStyle(
                    elementType = it.elementType as? String,
                    featureType = it.featureType as? String,
                    stylers = it.stylers as? Array<*>,
                )
            }.let { MapTypeStyles(it.toTypedArray()) }
        }
    }
}

data class MapTypeStyle(
    val elementType: String? = null,
    val featureType: String? = null,
    val stylers: Array<dynamic>? = null,
)

enum class MapTypeId {
    Hybrid,
    Roadmap,
    Satellite,
    Terrain,
}


/**
 * Options for the rendering of the fullscreen control.
 *
 * See [FullscreenControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#FullscreenControlOptions)
 * for more details
 */
data class FullscreenControlOptions(
    val position: ControlPosition? = null,
)

/**
 * Options for the rendering of the map type control.
 *
 * See [MapTypeControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#MapTypeControlOptions)
 * for more details
 */
data class MapTypeControlOptions(
    val mapTypeIds: Array<MapTypeId>? = null,
    val position: ControlPosition? = null,
    val style: MapTypeControlStyle? = null,
)

/**
 * See [MapTypeControlStyle](https://developers.google.com/maps/documentation/javascript/reference/control#MapTypeControlStyle)
 * for more details
 */
enum class MapTypeControlStyle {
    Default,
    DropdownMenu,
    HorizontalBar,
}

/**
 * Options for the rendering of the pan control.
 *
 * See [PanControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#PanControlOptions)
 * for more details
 */
data class PanControlOptions(
    val position: ControlPosition? = null,
)

/**
 * Options for the rendering of the rotate control.
 *
 * See [RotateControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#RotateControlOptions)
 * for more details
 */
data class RotateControlOptions(
    val position: ControlPosition? = null,
)

/**
 * Options for the rendering of the street view pegman control on the map.
 *
 * See [StreetViewControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#StreetViewControlOptions)
 * for more details
 */
data class StreetViewControlOptions(
    val position: ControlPosition? = null,
)

/**
 * Options for the rendering of the zoom control.
 *
 * See [ZoomControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#ZoomControlOptions)
 * for more details
 */
data class ZoomControlOptions(
    val position: ControlPosition? = null
)

/**
 * Used to specify the position of the controls on the map.
 *
 * See [ControlPosition](https://developers.google.com/maps/documentation/javascript/reference/control#ControlPosition)
 * for more details
 */
enum class ControlPosition {
    BottomCenter,
    BottomLeft,
    BottomRight,
    LeftBottom,
    LeftCenter,
    LeftTop,
    RightBottom,
    RightCenter,
    RightTop,
    TopCenter,
    TopLeft,
    TopRight,
}

data class MapRestriction(
    val latLngBounds: LatLngBounds,
    val strictBounds: Boolean? = null,
)