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

internal data class Events(
    val animationChanged: Event.Unit?,
    val clickableChanged: Event.Unit?,
    val contextMenu: Event.Mouse?,
    val cursorChanged: Event.Unit?,
    val dblclick: Event.Mouse?,
    val drag: Event.Mouse?,
    val dragend: Event.Mouse?,
    val draggableChanged: Event.Unit?,
    val dragStart: Event.Mouse?,
    val flatChanged: Event.Unit?,
    val iconChanged: Event.Unit?,
    val mousedown: Event.Mouse?,
    val mouseout: Event.Mouse?,
    val mouseover: Event.Mouse?,
    val mouseup: Event.Mouse?,
    val positionChanged: Event.Unit?,
    val shapeChanged: Event.Unit?,
    val titleChanged: Event.Unit?,
    val visibleChanged: Event.Unit?,
    val zindexChanged: Event.Unit?,
)

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
        onAnimationChanged?.let { Event.Unit("animation_changed", it) },
        onClickableChanged?.let { Event.Unit("clickable_changed", it) },
        onContextMenu?.let { Event.Mouse("contextmenu", it) },
        onCursorChanged?.let { Event.Unit("cursor_changed", it) },
        onDoubleClick?.let { Event.Mouse("dblclick", it) },
        onDrag?.let { Event.Mouse("drag", it) },
        onDragEnd?.let { Event.Mouse("dragend", it) },
        onDraggableChanged?.let { Event.Unit("draggable_changed", it) },
        onDragStart?.let { Event.Mouse("dragstart", it) },
        onFlatChanged?.let { Event.Unit("flat_changed", it) },
        onIconChanged?.let { Event.Unit("icon_changed", it) },
        onMouseDown?.let { Event.Mouse("mousedown", it) },
        onMouseOut?.let { Event.Mouse("mouseout", it) },
        onMouseOver?.let { Event.Mouse("mouseover", it) },
        onMouseUp?.let { Event.Mouse("mouseup", it) },
        onPositionChanged?.let { Event.Unit("position_changed", it) },
        onShapeChanged?.let { Event.Unit("shape_changed", it) },
        onTitleChanged?.let { Event.Unit("title_changed", it) },
        onVisibleChanged?.let { Event.Unit("visible_changed", it) },
        onZIndexChanged?.let { Event.Unit("zindex_changed", it) },
    )
}

internal fun <R> Events.map(transform: (Event) -> R): List<R> {
    val list = mutableListOf<R>()
    animationChanged?.let { list.add(transform(it)) }
    clickableChanged?.let { list.add(transform(it)) }
    contextMenu?.let { list.add(transform(it)) }
    cursorChanged?.let { list.add(transform(it)) }
    dblclick?.let { list.add(transform(it)) }
    drag?.let { list.add(transform(it)) }
    dragend?.let { list.add(transform(it)) }
    draggableChanged?.let { list.add(transform(it)) }
    dragStart?.let { list.add(transform(it)) }
    flatChanged?.let { list.add(transform(it)) }
    iconChanged?.let { list.add(transform(it)) }
    mousedown?.let { list.add(transform(it)) }
    mouseout?.let { list.add(transform(it)) }
    mouseover?.let { list.add(transform(it)) }
    mouseup?.let { list.add(transform(it)) }
    positionChanged?.let { list.add(transform(it)) }
    shapeChanged?.let { list.add(transform(it)) }
    titleChanged?.let { list.add(transform(it)) }
    visibleChanged?.let { list.add(transform(it)) }
    zindexChanged?.let { list.add(transform(it)) }
    return list
}
