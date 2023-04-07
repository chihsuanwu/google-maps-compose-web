package com.chihsuanwu.maps.compose.web.jsobject

import com.chihsuanwu.maps.compose.web.*
import js.core.jso


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
    var fullscreenControlOptions: JsFullscreenControlOptions?
    var gestureHandling: String
    var isFractionalZoomEnabled: Boolean
    var keyboardShortcuts: Boolean
    var mapId: String
    var mapTypeControl: Boolean
    var mapTypeControlOptions: JsMapTypeControlOptions?
    var mapTypeId: String
    var maxZoom: Double
    var minZoom: Double
    var noClear: Boolean
    var panControl: Boolean
    var panControlOptions: JsPanControlOptions?
    var restriction: JsMapRestriction?
    var rotateControl: Boolean
    var rotateControlOptions: JsRotateControlOptions?
    var scaleControl: Boolean
    var scaleControlOptions: dynamic
    var scrollwheel: Boolean
    var streetView: dynamic
    var streetViewControl: Boolean
    var streetViewControlOptions: JsStreetViewControlOptions?
    var styles: Array<JsMapTypeStyle>?
    var zoomControl: Boolean
    var zoomControlOptions: JsZoomControlOptions?
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
        fullscreenControlOptions = opt.fullscreenControlOptions?.toJsFullscreenControlOptions()
        opt.gestureHandling?.let { gestureHandling = it }
        opt.isFractionalZoomEnabled?.let { isFractionalZoomEnabled = it }
        opt.keyboardShortcuts?.let { keyboardShortcuts = it }
        opt.mapId?.let { mapId = it }
        opt.mapTypeControl?.let { mapTypeControl = it }
        mapTypeControlOptions = opt.mapTypeControlOptions?.toJsMapTypeControlOptions()
        opt.mapTypeId?.let { mapTypeId = it.toJsTypeIdString() }
        opt.maxZoom?.let { maxZoom = it }
        opt.minZoom?.let { minZoom = it }
        opt.noClear?.let { noClear = it }
        opt.panControl?.let { panControl = it }
        panControlOptions = opt.panControlOptions?.toJsPanControlOptions()
        restriction = opt.restriction?.toJsMapRestriction()
        opt.rotateControl?.let { rotateControl = it }
        rotateControlOptions = opt.rotateControlOptions?.toJsRotateControlOptions()
        opt.scaleControl?.let { scaleControl = it }
        scaleControlOptions = opt.scaleControlOptions
        opt.scrollwheel?.let { scrollwheel = it }
        streetView = opt.streetView
        opt.streetViewControl?.let { streetViewControl = it }
        streetViewControlOptions = opt.streetViewControlOptions?.toJsStreetViewControlOptions()
        styles = opt.styles?.styles?.map { it.toJsMapTypeStyle() }?.toTypedArray()
        opt.zoomControl?.let { zoomControl = it }
        zoomControlOptions = opt.zoomControlOptions?.toJsZoomControlOptions()
    }
}

/**
 * [google.maps.MapTypeStyle](https://developers.google.com/maps/documentation/javascript/reference/map#MapTypeStyle)
 */
internal external interface JsMapTypeStyle {
    var elementType: String
    var featureType: String
    var stylers: Array<dynamic>
}

internal fun MapTypeStyle.toJsMapTypeStyle(): JsMapTypeStyle {
    val style = this
    return jso {
        style.elementType?.let { elementType = it }
        style.featureType?.let { featureType = it }
        style.stylers?.let { stylers = it.toTypedArray() }
    }
}

/**
 * See [google.maps.MapTypeId](https://developers.google.com/maps/documentation/javascript/reference/map#MapTypeId)
 */
internal fun MapTypeId.toJsTypeIdString(): String {
    return when (this) {
        MapTypeId.Hybrid -> "hybrid"
        MapTypeId.Roadmap -> "roadmap"
        MapTypeId.Satellite -> "satellite"
        MapTypeId.Terrain -> "terrain"
    }
}

/**
 * [google.maps.FullscreenControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#FullscreenControlOptions)
 */
internal external interface JsFullscreenControlOptions {
    var position: JsControlPosition
}

internal fun FullscreenControlOptions.toJsFullscreenControlOptions(): JsFullscreenControlOptions {
    val opt = this
    return jso {
        opt.position?.let { position = it.toJsControlPosition() }
    }
}

/**
 * [google.maps.MapTypeControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#MapTypeControlOptions)
 */
internal external interface JsMapTypeControlOptions {
    var mapTypeIds: Array<String>
    var position: JsControlPosition
    var style: JsMapTypeControlStyle
}

internal fun MapTypeControlOptions.toJsMapTypeControlOptions(): JsMapTypeControlOptions {
    val opt = this
    return jso {
        opt.mapTypeIds?.let { mapTypeIds = it.map { it.toJsTypeIdString() }.toTypedArray() }
        opt.position?.let { position = it.toJsControlPosition() }
        opt.style?.let { style = it.toJsMapTypeControlStyle() }
    }
}

/**
 * [google.maps.MapTypeControlStyle](https://developers.google.com/maps/documentation/javascript/reference/control#MapTypeControlStyle)
 */
internal external interface JsMapTypeControlStyle

internal fun MapTypeControlStyle.toJsMapTypeControlStyle(): JsMapTypeControlStyle {
    return when (this) {
        MapTypeControlStyle.Default -> js("google.maps.MapTypeControlStyle.DEFAULT")
        MapTypeControlStyle.DropdownMenu -> js("google.maps.MapTypeControlStyle.DROPDOWN_MENU")
        MapTypeControlStyle.HorizontalBar -> js("google.maps.MapTypeControlStyle.HORIZONTAL_BAR")
    }
}

/**
 * [google.maps.PanControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#PanControlOptions)
 */
internal external interface JsPanControlOptions {
    var position: JsControlPosition
}

internal fun PanControlOptions.toJsPanControlOptions(): JsPanControlOptions {
    val opt = this
    return jso {
        opt.position?.let { position = it.toJsControlPosition() }
    }
}

/**
 * [google.maps.RotateControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#RotateControlOptions)
 */
internal external interface JsRotateControlOptions {
    var position: JsControlPosition
}

internal fun RotateControlOptions.toJsRotateControlOptions(): JsRotateControlOptions {
    val opt = this
    return jso {
        opt.position?.let { position = it.toJsControlPosition() }
    }
}

/**
 * [google.maps.ScaleControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#ScaleControlOptions)
 */
internal external interface JsStreetViewControlOptions {
    var position: JsControlPosition
}

internal fun StreetViewControlOptions.toJsStreetViewControlOptions(): JsStreetViewControlOptions {
    val opt = this
    return jso {
        opt.position?.let { position = it.toJsControlPosition() }
    }
}

/**
 * [google.maps.ZoomControlOptions](https://developers.google.com/maps/documentation/javascript/reference/control#ZoomControlOptions)
 */
internal external interface JsZoomControlOptions {
    var position: JsControlPosition
}

internal fun ZoomControlOptions.toJsZoomControlOptions(): JsZoomControlOptions {
    val opt = this
    return jso {
        opt.position?.let { position = it.toJsControlPosition() }
    }
}

/**
 * [google.maps.ControlPosition](https://developers.google.com/maps/documentation/javascript/reference/control#ControlPosition)
 */
internal external interface JsControlPosition

internal fun ControlPosition.toJsControlPosition(): JsControlPosition {
    return when (this) {
        ControlPosition.BottomCenter -> js("google.maps.ControlPosition.BOTTOM_CENTER")
        ControlPosition.BottomLeft -> js("google.maps.ControlPosition.BOTTOM_LEFT")
        ControlPosition.BottomRight -> js("google.maps.ControlPosition.BOTTOM_RIGHT")
        ControlPosition.LeftBottom -> js("google.maps.ControlPosition.LEFT_BOTTOM")
        ControlPosition.LeftCenter -> js("google.maps.ControlPosition.LEFT_CENTER")
        ControlPosition.LeftTop -> js("google.maps.ControlPosition.LEFT_TOP")
        ControlPosition.RightBottom -> js("google.maps.ControlPosition.RIGHT_BOTTOM")
        ControlPosition.RightCenter -> js("google.maps.ControlPosition.RIGHT_CENTER")
        ControlPosition.RightTop -> js("google.maps.ControlPosition.RIGHT_TOP")
        ControlPosition.TopCenter -> js("google.maps.ControlPosition.TOP_CENTER")
        ControlPosition.TopLeft -> js("google.maps.ControlPosition.TOP_LEFT")
        ControlPosition.TopRight -> js("google.maps.ControlPosition.TOP_RIGHT")
    }
}

internal external interface JsMapRestriction {
    var latLngBounds: JsLatLngBoundsLiteral
    var strictBounds: Boolean
}

internal fun MapRestriction.toJsMapRestriction(): JsMapRestriction {
    val restriction = this
    return jso {
        restriction.latLngBounds.let { latLngBounds = it.toJsLatLngBoundsLiteral() }
        restriction.strictBounds?.let { strictBounds = it }
    }
}