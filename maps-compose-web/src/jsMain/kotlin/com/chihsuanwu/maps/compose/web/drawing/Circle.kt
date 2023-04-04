package com.chihsuanwu.maps.compose.web.drawing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.LatLng
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapMouseEvent
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.drawing.JsCircle
import com.chihsuanwu.maps.compose.web.jsobject.drawing.newCircle
import com.chihsuanwu.maps.compose.web.jsobject.drawing.toJs
import js.core.jso

internal class CircleNode(
    val circle: JsCircle,
    var events: MutableMap<String, MapsEventListener>
) : MapNode {
    override fun onRemoved() {
        circle.setMap(null)
    }
}

/**
 * A composable for a circle on the map.
 *
 * @param center the center of the circle
 * @param clickable Indicates whether this Circle handles mouse events.
 * @param draggable If set to true, the user can drag this shape over the map.
 * @param editable If set to true, the user can edit this circle by dragging the control points shown at the center
 * and around the circumference of the circle.
 * @param fillColor The fill color. All CSS3 colors are supported except for extended named colors.
 * @param fillOpacity The fill opacity between 0.0 and 1.0.
 * @param strokeColor The stroke color. All CSS3 colors are supported except for extended named colors.
 * @param strokeOpacity The stroke opacity between 0.0 and 1.0.
 * @param strokeWidth The stroke width in pixels.
 * @param strokePosition The stroke position. Defaults to CENTER.
 * @param visible Whether this circle is visible on the map.
 * @param zIndex The zIndex compared to other circles.
 *
 * @param onCenterChanged A callback to be invoked when the circle center is changed.
 * @param onClick A callback to be invoked when the circle is clicked.
 * @param onDoubleClick A callback to be invoked when the circle is double-clicked.
 * @param onDrag A callback to be invoked repeatedly while the user drags the circle.
 * @param onDragEnd A callback to be invoked when the user stops dragging the circle.
 * @param onDragStart A callback to be invoked when the user starts dragging the circle.
 * @param onMouseDown A callback to be invoked when the DOM mousedown event is fired on the circle.
 * @param onMouseMove A callback to be invoked when the DOM mousemove event is fired on the circle.
 * @param onMouseOut A callback to be invoked when the mouseout event is fired on the circle.
 * @param onMouseOver A callback to be invoked when the mouseover event is fired on the circle.
 * @param onMouseUp A callback to be invoked when the DOM mouseup event is fired on the circle.
 * @param onRadiusChanged A callback to be invoked when the circle radius is changed.
 * @param onRightClick A callback to be invoked when the circle is right-clicked.
 */
@Composable
fun Circle(
    center: LatLng,
    clickable: Boolean = true,
    draggable: Boolean = false,
    editable: Boolean = false,
    fillColor: String = "#000000",
    fillOpacity: Double = 1.0,
    radius: Double = 0.0,
    strokeColor: String = "#000000",
    strokeOpacity: Double = 1.0,
    strokeWidth: Int = 5,
    strokePosition: StrokePosition = StrokePosition.CENTER,
    visible: Boolean = true,
    zIndex: Double? = null,
    onCenterChanged: () -> Unit = {},
    onClick: (MapMouseEvent) -> Unit = {},
    onDoubleClick: (MapMouseEvent) -> Unit = {},
    onDrag: (MapMouseEvent) -> Unit = {},
    onDragEnd: (MapMouseEvent) -> Unit = {},
    onDragStart: (MapMouseEvent) -> Unit = {},
    onMouseDown: (MapMouseEvent) -> Unit = {},
    onMouseMove: (MapMouseEvent) -> Unit = {},
    onMouseOut: (MapMouseEvent) -> Unit = {},
    onMouseOver: (MapMouseEvent) -> Unit = {},
    onMouseUp: (MapMouseEvent) -> Unit = {},
    onRadiusChanged: () -> Unit = {},
    onRightClick: (MapMouseEvent) -> Unit = {},
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<CircleNode, MapApplier>(
        factory = {
            val circle = newCircle(
                jso {
                    this.center = center.toJsLatLngLiteral()
                    this.clickable = clickable
                    this.draggable = draggable
                    this.editable = editable
                    this.fillColor = fillColor
                    this.fillOpacity = fillOpacity
                    this.map = mapApplier?.map
                    this.radius = radius
                    this.strokeColor = strokeColor
                    this.strokeOpacity = strokeOpacity
                    this.strokeWeight = strokeWidth
                    this.strokePosition = strokePosition.toJs()
                    this.visible = visible
                    this.zIndex = zIndex
                }
            )
            CircleNode(circle, mutableMapOf())
        },
        update = {
            set(center) { circle.setOptions(jso { this.center = center.toJsLatLngLiteral() }) }
            set(clickable) { circle.setOptions(jso { this.clickable = clickable }) }
            set(draggable) { circle.setOptions(jso { this.draggable = draggable }) }
            set(editable) { circle.setOptions(jso { this.editable = editable }) }
            set(fillColor) { circle.setOptions(jso { this.fillColor = fillColor }) }
            set(fillOpacity) { circle.setOptions(jso { this.fillOpacity = fillOpacity }) }
            set(radius) { circle.setOptions(jso { this.radius = radius }) }
            set(strokeColor) { circle.setOptions(jso { this.strokeColor = strokeColor }) }
            set(strokeOpacity) { circle.setOptions(jso { this.strokeOpacity = strokeOpacity }) }
            set(strokeWidth) { circle.setOptions(jso { this.strokeWeight = strokeWidth }) }
            set(strokePosition) { circle.setOptions(jso { this.strokePosition = strokePosition.toJs() }) }
            set(visible) { circle.setOptions(jso { this.visible = visible }) }
            set(zIndex) { circle.setOptions(jso { this.zIndex = zIndex }) }

            set(onCenterChanged) {
                val eventName = "center_changed"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onCenterChanged() }
            }
            set(onClick) {
                val eventName = "click"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onClick((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDoubleClick) {
                val eventName = "dblclick"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onDoubleClick((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDrag) {
                val eventName = "drag"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onDrag((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragEnd) {
                val eventName = "dragend"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onDragEnd((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragStart) {
                val eventName = "dragstart"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onDragStart((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseDown) {
                val eventName = "mousedown"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onMouseDown((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseMove) {
                val eventName = "mousemove"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onMouseMove((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseOut) {
                val eventName = "mouseout"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onMouseOut((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseOver) {
                val eventName = "mouseover"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onMouseOver((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseUp) {
                val eventName = "mouseup"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onMouseUp((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onRadiusChanged) {
                val eventName = "radius_changed"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onRadiusChanged() }
            }
            set(onRightClick) {
                val eventName = "rightclick"
                events[eventName]?.remove()
                events[eventName] = circle.addListener(eventName) { onRightClick((it as JsMapMouseEvent).toMouseEvent()) }
            }
        }
    )
}