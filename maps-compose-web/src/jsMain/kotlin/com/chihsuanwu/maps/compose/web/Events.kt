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
internal data class Events(
    val animationChanged: Event.Unit?,
    val boundsChanged: Event.Unit?,
    val centerChanged: Event.Unit?,
    val clickableChanged: Event.Unit?,
    val contextMenu: Event.Mouse?,
    val cursorChanged: Event.Unit?,
    val dblclick: Event.Mouse?,
    val drag: Event.Mouse?,
    val dragend: Event.Mouse?,
    val draggableChanged: Event.Unit?,
    val dragStart: Event.Mouse?,
    val mapDrag: Event.Unit?,
    val mapDragend: Event.Unit?,
    val mapDragStart: Event.Unit?,
    val flatChanged: Event.Unit?,
    val headingChanged: Event.Unit?,
    val iconChanged: Event.Unit?,
    val idle: Event.Unit?,
    val isFractionalZoomEnabledChanged: Event.Unit?,
    val maptypeidChanged: Event.Unit?,
    val mousedown: Event.Mouse?,
    val mousemove: Event.Mouse?,
    val mouseout: Event.Mouse?,
    val mouseover: Event.Mouse?,
    val mouseup: Event.Mouse?,
    val positionChanged: Event.Unit?,
    val projectionChanged: Event.Unit?,
    val renderingTypeChanged: Event.Unit?,
    val shapeChanged: Event.Unit?,
    val tilesloaded: Event.Unit?,
    val tiltChanged: Event.Unit?,
    val titleChanged: Event.Unit?,
    val visibleChanged: Event.Unit?,
    val zindexChanged: Event.Unit?,
    val zoomChanged: Event.Unit?,
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
        centerChanged = null,
        clickableChanged = null,
        contextMenu = onContextMenu?.let { Event.Mouse("contextmenu", it) },
        cursorChanged = onCursorChanged?.let { Event.Unit("cursor_changed", it) },
        dblclick = onDoubleClick?.let { Event.Mouse("dblclick", it) },
        drag = onDrag?.let { Event.Mouse("drag", it) },
        dragend = onDragEnd?.let { Event.Mouse("dragend", it) },
        draggableChanged = onDraggableChanged?.let { Event.Unit("draggable_changed", it) },
        dragStart = onDragStart?.let { Event.Mouse("dragstart", it) },
        mapDrag = null,
        mapDragend = null,
        mapDragStart = null,
        flatChanged = onFlatChanged?.let { Event.Unit("flat_changed", it) },
        headingChanged = null,
        iconChanged = onIconChanged?.let { Event.Unit("icon_changed", it) },
        idle = null,
        isFractionalZoomEnabledChanged = null,
        maptypeidChanged = null,
        mousedown = onMouseDown?.let { Event.Mouse("mousedown", it) },
        mousemove = null,
        mouseout = onMouseOut?.let { Event.Mouse("mouseout", it) },
        mouseover = onMouseOver?.let { Event.Mouse("mouseover", it) },
        mouseup = onMouseUp?.let { Event.Mouse("mouseup", it) },
        positionChanged = onPositionChanged?.let { Event.Unit("position_changed", it) },
        projectionChanged = null,
        renderingTypeChanged = null,
        shapeChanged = onShapeChanged?.let { Event.Unit("shape_changed", it) },
        tilesloaded = null,
        tiltChanged = null,
        titleChanged = onTitleChanged?.let { Event.Unit("title_changed", it) },
        visibleChanged = onVisibleChanged?.let { Event.Unit("visible_changed", it) },
        zindexChanged = onZIndexChanged?.let { Event.Unit("zindex_changed", it) },
        zoomChanged = null,
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
    var onMaptypeidChanged: ((Unit) -> Unit)? = null
    var onMouseMove: ((e: MouseEvent) -> Unit)? = null
    var onMouseOut: ((e: MouseEvent) -> Unit)? = null
    var onMouseOver: ((e: MouseEvent) -> Unit)? = null
    var onProjectionChanged: ((Unit) -> Unit)? = null
    var onRenderingTypeChanged: ((Unit) -> Unit)? = null
    var onTilesloaded: ((Unit) -> Unit)? = null
    var onTiltChanged: ((Unit) -> Unit)? = null
    var onTitleChanged: ((Unit) -> Unit)? = null
    var onZoomChanged: ((Unit) -> Unit)? = null

    internal fun build() = Events(
        animationChanged = null,
        boundsChanged = onBoundsChanged?.let { Event.Unit("bounds_changed", it) },
        centerChanged = onCenterChanged?.let { Event.Unit("center_changed", it) },
        clickableChanged = null,
        contextMenu = onContextMenu?.let { Event.Mouse("contextmenu", it) },
        cursorChanged = null,
        dblclick = onDoubleClick?.let { Event.Mouse("dblclick", it) },
        drag = null,
        dragend = null,
        draggableChanged = null,
        dragStart = null,
        mapDrag = onDrag?.let { Event.Unit("drag", it) },
        mapDragend = onDragEnd?.let { Event.Unit("dragend", it) },
        mapDragStart = onDragStart?.let { Event.Unit("dragstart", it) },
        flatChanged = null,
        headingChanged = onHeadingChanged?.let { Event.Unit("heading_changed", it) },
        iconChanged = null,
        idle = onIdle?.let { Event.Unit("idle", it) },
        isFractionalZoomEnabledChanged = onIsFractionalZoomEnabledChanged?.let { Event.Unit("isFractionalZoomEnabled_changed", it) },
        maptypeidChanged = onMaptypeidChanged?.let { Event.Unit("maptypeid_changed", it) },
        mousedown = null,
        mousemove = onMouseMove?.let { Event.Mouse("mousemove", it) },
        mouseout = onMouseOut?.let { Event.Mouse("mouseout", it) },
        mouseover = onMouseOver?.let { Event.Mouse("mouseover", it) },
        mouseup = null,
        positionChanged = null,
        projectionChanged = onProjectionChanged?.let { Event.Unit("projection_changed", it) },
        renderingTypeChanged = onRenderingTypeChanged?.let { Event.Unit("renderingType_changed", it) },
        shapeChanged = null,
        tilesloaded = onTilesloaded?.let { Event.Unit("tilesloaded", it) },
        tiltChanged = onTiltChanged?.let { Event.Unit("tilt_changed", it) },
        titleChanged = onTitleChanged?.let { Event.Unit("title_changed", it) },
        visibleChanged = null,
        zindexChanged = null,
        zoomChanged = onZoomChanged?.let { Event.Unit("zoom_changed", it) },
    )
}

internal fun <R> Events.map(transform: (Event) -> R): List<R> {
    val list = mutableListOf<R>()
    animationChanged?.let { list.add(transform(it)) }
    boundsChanged?.let { list.add(transform(it)) }
    centerChanged?.let { list.add(transform(it)) }
    clickableChanged?.let { list.add(transform(it)) }
    contextMenu?.let { list.add(transform(it)) }
    cursorChanged?.let { list.add(transform(it)) }
    dblclick?.let { list.add(transform(it)) }
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
    maptypeidChanged?.let { list.add(transform(it)) }
    mousedown?.let { list.add(transform(it)) }
    mousemove?.let { list.add(transform(it)) }
    mouseout?.let { list.add(transform(it)) }
    mouseover?.let { list.add(transform(it)) }
    mouseup?.let { list.add(transform(it)) }
    positionChanged?.let { list.add(transform(it)) }
    projectionChanged?.let { list.add(transform(it)) }
    renderingTypeChanged?.let { list.add(transform(it)) }
    shapeChanged?.let { list.add(transform(it)) }
    tilesloaded?.let { list.add(transform(it)) }
    tiltChanged?.let { list.add(transform(it)) }
    titleChanged?.let { list.add(transform(it)) }
    visibleChanged?.let { list.add(transform(it)) }
    zindexChanged?.let { list.add(transform(it)) }
    zoomChanged?.let { list.add(transform(it)) }
    return list
}
