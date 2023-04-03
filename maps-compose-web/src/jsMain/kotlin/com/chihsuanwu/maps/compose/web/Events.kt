package com.chihsuanwu.maps.compose.web


class MouseEvent(
    val latLng: LatLng
)

internal sealed class Event(
    val event: String,
    val callback: (dynamic) -> kotlin.Unit
) {
    internal class Unit(
        event: String,
        callback: (kotlin.Unit) -> kotlin.Unit
    ) : Event(event, callback)

    internal class Mouse(
        event: String,
        callback: (e: MouseEvent) -> kotlin.Unit
    ) : Event(event, callback)
}

/**
 * All events in Google Maps API
 * Each component has its own EventsBuilder to build its own Events.
 */
internal class Events(
    val animationChanged: Event.Unit? = null,
    val boundsChanged: Event.Unit? = null,
    val centerChanged: Event.Unit? = null,
    val clickableChanged: Event.Unit? = null,
    val closeClick: Event.Unit? = null,
    val contextMenu: Event.Mouse? = null,
    val contentChanged: Event.Unit? = null,
    val cursorChanged: Event.Unit? = null,
    val domReady: Event.Unit? = null,
    val dblclick: Event.Mouse? = null,
    val drag: Event.Mouse? = null,
    val dragend: Event.Mouse? = null,
    val draggableChanged: Event.Unit? = null,
    val dragStart: Event.Mouse? = null,
    val mapDrag: Event.Unit? = null,
    val mapDragend: Event.Unit? = null,
    val mapDragStart: Event.Unit? = null,
    val flatChanged: Event.Unit? = null,
    val headingChanged: Event.Unit? = null,
    val iconChanged: Event.Unit? = null,
    val idle: Event.Unit? = null,
    val isFractionalZoomEnabledChanged: Event.Unit? = null,
    val mapTypeIdChanged: Event.Unit? = null,
    val mousedown: Event.Mouse? = null,
    val mousemove: Event.Mouse? = null,
    val mouseout: Event.Mouse? = null,
    val mouseover: Event.Mouse? = null,
    val mouseup: Event.Mouse? = null,
    val positionChanged: Event.Unit? = null,
    val projectionChanged: Event.Unit? = null,
    val renderingTypeChanged: Event.Unit? = null,
    val shapeChanged: Event.Unit? = null,
    val tilesLoaded: Event.Unit? = null,
    val tiltChanged: Event.Unit? = null,
    val titleChanged: Event.Unit? = null,
    val visible: Event.Unit? = null,
    val visibleChanged: Event.Unit? = null,
    val zIndexChanged: Event.Unit? = null,
    val zoomChanged: Event.Unit? = null,
)

/**
 * EventsBuilder for drawing components like [Circle], [Marker], etc.
 */
class EventsBuilder {
    var onAnimationChanged: ((Unit) -> Unit)? = null
    var onClickableChanged: ((Unit) -> Unit)? = null
    var onContextMenu: ((e: MouseEvent) -> Unit)? = null
    var onCursorChanged: ((Unit) -> Unit)? = null
    var onDoubleClick: ((e: MouseEvent) -> Unit)? = null
    var onDrag: ((e: MouseEvent) -> Unit)? = null
    var onDragEnd: ((e: MouseEvent) -> Unit)? = null
    var onDraggableChanged: ((Unit) -> Unit)? = null
    var onDragStart: ((e: MouseEvent) -> Unit)? = null
    var onFlatChanged: ((Unit) -> Unit)? = null
    var onIconChanged: ((Unit) -> Unit)? = null
    var onMouseDown: ((e: MouseEvent) -> Unit)? = null
    var onMouseOut: ((e: MouseEvent) -> Unit)? = null
    var onMouseOver: ((e: MouseEvent) -> Unit)? = null
    var onMouseUp: ((e: MouseEvent) -> Unit)? = null
    var onPositionChanged: ((Unit) -> Unit)? = null
    var onShapeChanged: ((Unit) -> Unit)? = null
    var onTitleChanged: ((Unit) -> Unit)? = null
    var onVisibleChanged: ((Unit) -> Unit)? = null
    var onZIndexChanged: ((Unit) -> Unit)? = null

    internal fun build() = Events(
        animationChanged = onAnimationChanged?.let { Event.Unit("animation_changed", it) },
        boundsChanged = onClickableChanged?.let { Event.Unit("clickable_changed", it) },
        contextMenu = onContextMenu?.let { Event.Mouse("contextmenu", it) },
        cursorChanged = onCursorChanged?.let { Event.Unit("cursor_changed", it) },
        dblclick = onDoubleClick?.let { Event.Mouse("dblclick", it) },
        drag = onDrag?.let { Event.Mouse("drag", it) },
        dragend = onDragEnd?.let { Event.Mouse("dragend", it) },
        draggableChanged = onDraggableChanged?.let { Event.Unit("draggable_changed", it) },
        dragStart = onDragStart?.let { Event.Mouse("dragstart", it) },
        flatChanged = onFlatChanged?.let { Event.Unit("flat_changed", it) },
        iconChanged = onIconChanged?.let { Event.Unit("icon_changed", it) },
        mousedown = onMouseDown?.let { Event.Mouse("mousedown", it) },
        mouseout = onMouseOut?.let { Event.Mouse("mouseout", it) },
        mouseover = onMouseOver?.let { Event.Mouse("mouseover", it) },
        mouseup = onMouseUp?.let { Event.Mouse("mouseup", it) },
        positionChanged = onPositionChanged?.let { Event.Unit("position_changed", it) },
        shapeChanged = onShapeChanged?.let { Event.Unit("shape_changed", it) },
        titleChanged = onTitleChanged?.let { Event.Unit("title_changed", it) },
        visibleChanged = onVisibleChanged?.let { Event.Unit("visible_changed", it) },
        zIndexChanged = onZIndexChanged?.let { Event.Unit("zindex_changed", it) },
    )
}

/**
 * EventsBuilder for [GoogleMap]
 */
class MapEventsBuilder {
    var onBoundsChanged: ((Unit) -> Unit)? = null
    var onCenterChanged: ((Unit) -> Unit)? = null
    var onContextMenu: ((e: MouseEvent) -> Unit)? = null
    var onDoubleClick: ((e: MouseEvent) -> Unit)? = null
    var onDrag: ((Unit) -> Unit)? = null
    var onDragEnd: ((Unit) -> Unit)? = null
    var onDragStart: ((Unit) -> Unit)? = null
    var onHeadingChanged: ((Unit) -> Unit)? = null
    var onIdle: ((Unit) -> Unit)? = null
    var onIsFractionalZoomEnabledChanged: ((Unit) -> Unit)? = null
    var onMapTypeIdChanged: ((Unit) -> Unit)? = null
    var onMouseMove: ((e: MouseEvent) -> Unit)? = null
    var onMouseOut: ((e: MouseEvent) -> Unit)? = null
    var onMouseOver: ((e: MouseEvent) -> Unit)? = null
    var onProjectionChanged: ((Unit) -> Unit)? = null
    var onRenderingTypeChanged: ((Unit) -> Unit)? = null
    var onTilesLoaded: ((Unit) -> Unit)? = null
    var onTiltChanged: ((Unit) -> Unit)? = null
    var onTitleChanged: ((Unit) -> Unit)? = null
    var onZoomChanged: ((Unit) -> Unit)? = null

    internal fun build() = Events(
        boundsChanged = onBoundsChanged?.let { Event.Unit("bounds_changed", it) },
        centerChanged = onCenterChanged?.let { Event.Unit("center_changed", it) },
        contextMenu = onContextMenu?.let { Event.Mouse("contextmenu", it) },
        dblclick = onDoubleClick?.let { Event.Mouse("dblclick", it) },
        mapDrag = onDrag?.let { Event.Unit("drag", it) },
        mapDragend = onDragEnd?.let { Event.Unit("dragend", it) },
        mapDragStart = onDragStart?.let { Event.Unit("dragstart", it) },
        headingChanged = onHeadingChanged?.let { Event.Unit("heading_changed", it) },
        idle = onIdle?.let { Event.Unit("idle", it) },
        isFractionalZoomEnabledChanged = onIsFractionalZoomEnabledChanged?.let { Event.Unit("isFractionalZoomEnabled_changed", it) },
        mapTypeIdChanged = onMapTypeIdChanged?.let { Event.Unit("maptypeid_changed", it) },
        mousemove = onMouseMove?.let { Event.Mouse("mousemove", it) },
        mouseout = onMouseOut?.let { Event.Mouse("mouseout", it) },
        mouseover = onMouseOver?.let { Event.Mouse("mouseover", it) },
        projectionChanged = onProjectionChanged?.let { Event.Unit("projection_changed", it) },
        renderingTypeChanged = onRenderingTypeChanged?.let { Event.Unit("renderingType_changed", it) },
        tilesLoaded = onTilesLoaded?.let { Event.Unit("tilesloaded", it) },
        tiltChanged = onTiltChanged?.let { Event.Unit("tilt_changed", it) },
        titleChanged = onTitleChanged?.let { Event.Unit("title_changed", it) },
        zoomChanged = onZoomChanged?.let { Event.Unit("zoom_changed", it) },
    )
}

/**
 * EventsBuilder for [InfoWindow]
 */
class InfoWindowEventsBuilder {
    var onCloseClick: ((Unit) -> Unit)? = null
    var onContentChanged: ((Unit) -> Unit)? = null
    var onDomReady: ((Unit) -> Unit)? = null
    var onPositionChanged: ((Unit) -> Unit)? = null
    val onVisible: ((Unit) -> Unit)? = null
    var onZIndexChanged: ((Unit) -> Unit)? = null

    internal fun build() = Events(
        closeClick = onCloseClick?.let { Event.Unit("closeclick", it) },
        contentChanged = onContentChanged?.let { Event.Unit("content_changed", it) },
        domReady = onDomReady?.let { Event.Unit("domready", it) },
        positionChanged = onPositionChanged?.let { Event.Unit("position_changed", it) },
        visible = onVisible?.let { Event.Unit("visible", it) },
        zIndexChanged = onZIndexChanged?.let { Event.Unit("zindex_changed", it) },
    )
}

internal fun <R> Events.map(transform: (Event) -> R): List<R> {
    val list = mutableListOf<R>()
    animationChanged?.let { list.add(transform(it)) }
    boundsChanged?.let { list.add(transform(it)) }
    centerChanged?.let { list.add(transform(it)) }
    clickableChanged?.let { list.add(transform(it)) }
    closeClick?.let { list.add(transform(it)) }
    contextMenu?.let { list.add(transform(it)) }
    contentChanged?.let { list.add(transform(it)) }
    cursorChanged?.let { list.add(transform(it)) }
    dblclick?.let { list.add(transform(it)) }
    domReady?.let { list.add(transform(it)) }
    drag?.let { list.add(transform(it)) }
    dragend?.let { list.add(transform(it)) }
    draggableChanged?.let { list.add(transform(it)) }
    dragStart?.let { list.add(transform(it)) }
    mapDrag?.let { list.add(transform(it)) }
    mapDragend?.let { list.add(transform(it)) }
    mapDragStart?.let { list.add(transform(it)) }
    flatChanged?.let { list.add(transform(it)) }
    headingChanged?.let { list.add(transform(it)) }
    iconChanged?.let { list.add(transform(it)) }
    idle?.let { list.add(transform(it)) }
    isFractionalZoomEnabledChanged?.let { list.add(transform(it)) }
    mapTypeIdChanged?.let { list.add(transform(it)) }
    mousedown?.let { list.add(transform(it)) }
    mousemove?.let { list.add(transform(it)) }
    mouseout?.let { list.add(transform(it)) }
    mouseover?.let { list.add(transform(it)) }
    mouseup?.let { list.add(transform(it)) }
    positionChanged?.let { list.add(transform(it)) }
    projectionChanged?.let { list.add(transform(it)) }
    renderingTypeChanged?.let { list.add(transform(it)) }
    shapeChanged?.let { list.add(transform(it)) }
    tilesLoaded?.let { list.add(transform(it)) }
    tiltChanged?.let { list.add(transform(it)) }
    titleChanged?.let { list.add(transform(it)) }
    visible?.let { list.add(transform(it)) }
    visibleChanged?.let { list.add(transform(it)) }
    zIndexChanged?.let { list.add(transform(it)) }
    zoomChanged?.let { list.add(transform(it)) }
    return list
}
