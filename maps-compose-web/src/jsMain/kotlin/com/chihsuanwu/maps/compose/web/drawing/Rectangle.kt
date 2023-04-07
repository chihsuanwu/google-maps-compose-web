package com.chihsuanwu.maps.compose.web.drawing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.*
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.drawing.JsRectangle
import com.chihsuanwu.maps.compose.web.jsobject.drawing.newRectangle
import com.chihsuanwu.maps.compose.web.jsobject.drawing.toJs
import js.core.jso


internal class RectangleNode(
    val rectangle: JsRectangle,
    var events: MutableMap<String, MapsEventListener>,
) : MapNode {
    override fun onRemoved() {
        rectangle.setMap(null)
    }
}

/**
 * A composable for a rectangle on the map.
 *
 * @param clickable Indicates whether this Rectangle handles mouse events.
 * @param draggable If set to true, the user can drag this shape over the map.
 * @param editable If set to true, the user can edit this rectangle by
 * dragging the control points shown at the corners and on each edge.
 * @param fillColor The fill color. All CSS3 colors are supported except for extended named colors.
 * @param fillOpacity The fill opacity between 0.0 and 1.0.
 * @param strokeColor The stroke color. All CSS3 colors are supported except for extended named colors.
 * @param strokeOpacity The stroke opacity between 0.0 and 1.0.
 * @param strokeWidth The stroke width in pixels.
 * @param strokePosition The stroke position. Defaults to CENTER.
 * @param visible Whether this rectangle is visible on the map.
 * @param zIndex The zIndex compared to other polys.
 *
 * @param onClick A callback to be invoked when the rectangle is clicked.
 * @param onContextMenu A callback to be invoked when the DOM contextmenu event is fired on the rectangle.
 * @param onDoubleClick A callback to be invoked when the rectangle is double-clicked.
 * @param onDrag A callback to be invoked repeatedly while the user drags the rectangle.
 * @param onDragEnd A callback to be invoked when the user stops dragging the rectangle.
 * @param onDragStart A callback to be invoked when the user stops dragging the rectangle.
 * @param onMouseDown A callback to be invoked when the DOM mousedown event is fired on the rectangle.
 * @param onMouseMove A callback to be invoked when the DOM mousemove event is fired on the rectangle.
 * @param onMouseOut A callback to be invoked when the mouseout event is fired.
 * @param onMouseOver A callback to be invoked when the mouseover event is fired.
 * @param onMouseUp A callback to be invoked when the DOM mouseup event is fired.
 */
@Composable
fun Rectangle(
    bounds: LatLngBounds,
    clickable: Boolean = true,
    draggable: Boolean = false,
    editable: Boolean = false,
    fillColor: String = "#000000",
    fillOpacity: Double = 1.0,
    strokeColor: String = "#000000",
    strokeOpacity: Double = 1.0,
    strokeWidth: Int = 5,
    strokePosition: StrokePosition = StrokePosition.CENTER,
    visible: Boolean = true,
    zIndex: Double? = null,
    onClick: (PolyMouseEvent) -> Unit = {},
    onContextMenu: (PolyMouseEvent) -> Unit = {},
    onDoubleClick: (PolyMouseEvent) -> Unit = {},
    onDrag: (MapMouseEvent) -> Unit = {},
    onDragEnd: (MapMouseEvent) -> Unit = {},
    onDragStart: (MapMouseEvent) -> Unit = {},
    onMouseDown: (PolyMouseEvent) -> Unit = {},
    onMouseMove: (PolyMouseEvent) -> Unit = {},
    onMouseOut: (PolyMouseEvent) -> Unit = {},
    onMouseOver: (PolyMouseEvent) -> Unit = {},
    onMouseUp: (PolyMouseEvent) -> Unit = {},
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<RectangleNode, MapApplier>(
        factory = {
            val rectangle = newRectangle(
                jso {
                    this.bounds = bounds.toJsLatLngBoundsLiteral()
                    this.clickable = clickable
                    this.draggable = draggable
                    this.editable = editable
                    this.fillColor = fillColor
                    this.fillOpacity = fillOpacity
                    this.map = mapApplier?.map
                    this.strokeColor = strokeColor
                    this.strokeOpacity = strokeOpacity
                    this.strokeWeight = strokeWidth
                    this.strokePosition = strokePosition.toJs()
                    this.visible = visible
                    this.zIndex = zIndex
                }
            )
            RectangleNode(rectangle, mutableMapOf())
        },
        update = {
            set(bounds) { rectangle.setOptions(jso { this.bounds = bounds.toJsLatLngBoundsLiteral() }) }
            set(clickable) { rectangle.setOptions(jso { this.clickable = clickable }) }
            set(draggable) { rectangle.setOptions(jso { this.draggable = draggable }) }
            set(editable) { rectangle.setOptions(jso { this.editable = editable }) }
            set(fillColor) { rectangle.setOptions(jso { this.fillColor = fillColor }) }
            set(fillOpacity) { rectangle.setOptions(jso { this.fillOpacity = fillOpacity }) }
            set(strokeColor) { rectangle.setOptions(jso { this.strokeColor = strokeColor }) }
            set(strokeOpacity) { rectangle.setOptions(jso { this.strokeOpacity = strokeOpacity }) }
            set(strokeWidth) { rectangle.setOptions(jso { this.strokeWeight = strokeWidth }) }
            set(strokePosition) { rectangle.setOptions(jso { this.strokePosition = strokePosition.toJs() }) }
            set(visible) { rectangle.setOptions(jso { this.visible = visible }) }
            set(zIndex) { rectangle.setOptions(jso { this.zIndex = zIndex }) }

            set(onClick) {
                val eventName = "click"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onClick((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onContextMenu) {
                val eventName = "contextmenu"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onContextMenu((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onDoubleClick) {
                val eventName = "dblclick"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onDoubleClick((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onDrag) {
                val eventName = "drag"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onDrag((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragEnd) {
                val eventName = "dragend"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onDragEnd((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragStart) {
                val eventName = "dragstart"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onDragStart((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseDown) {
                val eventName = "mousedown"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onMouseDown((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseMove) {
                val eventName = "mousemove"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onMouseMove((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseOut) {
                val eventName = "mouseout"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onMouseOut((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseOver) {
                val eventName = "mouseover"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onMouseOver((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseUp) {
                val eventName = "mouseup"
                events[eventName]?.remove()
                events[eventName] = rectangle.addListener(eventName) { onMouseUp((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
        }
    )
}