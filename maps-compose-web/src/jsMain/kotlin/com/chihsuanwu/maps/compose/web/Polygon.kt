package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.*
import js.core.jso

enum class StrokePosition {
    CENTER,
    INSIDE,
    OUTSIDE
}

internal class PolygonNode(
    val polygon: JsPolygon,
    var events: MutableMap<String, MapsEventListener>,
) : MapNode {
    override fun onRemoved() {
        polygon.setMap(null)
    }
}

/**
 * A composable for a polygon on the map.
 *
 * @param points The points of the polygon.
 *
 * @param clickable Indicates whether this Polygon handles mouse events.
 * @param draggable If set to true, the user can drag this shape over the map.
 * The geodesic property defines the mode of dragging.
 * @param editable If set to true, the user can edit this shape by dragging the control points
 * shown at the vertices and on each segment.
 * @param fillColor The fill color. All CSS3 colors are supported except for extended named colors.
 * @param fillOpacity The fill opacity between 0.0 and 1.0.
 * @param geodesic When true, edges of the polygon are interpreted as geodesic and
 * will follow the curvature of the Earth. When false, edges of the polygon are
 * rendered as straight lines in screen space.
 * @param strokeColor The stroke color. All CSS3 colors are supported except for extended named colors.
 * @param strokeOpacity The stroke opacity between 0.0 and 1.0.
 * @param strokeWidth The stroke width in pixels.
 * @param strokePosition The stroke position. Defaults to CENTER.
 * @param visible Whether this polygon is visible on the map.
 * @param zIndex The zIndex compared to other polys.
 *
 * @param onClick A callback to be invoked when the polygon is clicked.
 * @param onContextMenu A callback to be invoked when the DOM contextmenu event is fired on the polygon.
 * @param onDoubleClick A callback to be invoked when the polygon is double-clicked.
 * @param onDrag A callback to be invoked repeatedly while the user drags the polygon.
 * @param onDragEnd A callback to be invoked when the user stops dragging the polygon.
 * @param onDragStart A callback to be invoked when the user stops dragging the polygon.
 * @param onMouseDown A callback to be invoked when the DOM mousedown event is fired on the polygon.
 * @param onMouseMove A callback to be invoked when the DOM mousemove event is fired on the polygon.
 * @param onMouseOut A callback to be invoked when the mouseout event is fired.
 * @param onMouseOver A callback to be invoked when the mouseover event is fired.
 * @param onMouseUp A callback to be invoked when the DOM mouseup event is fired.
 *
 */
@Composable
fun Polygon(
    points: List<LatLng>,
    clickable: Boolean = true,
    draggable: Boolean = false,
    editable: Boolean = false,
    fillColor: String = "#000000",
    fillOpacity: Double = 1.0,
    geodesic: Boolean = false,
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
    ComposeNode<PolygonNode, MapApplier>(
        factory = {
            val polygon = newPolygon(
                jso {
                    this.paths = points.toJsLatLngLiteralArray()
                    this.clickable = clickable
                    this.fillColor = fillColor
                    this.fillOpacity = fillOpacity
                    this.draggable = draggable
                    this.editable = editable
                    this.geodesic = geodesic
                    this.map = mapApplier?.map
                    this.strokeColor = strokeColor
                    this.strokeOpacity = strokeOpacity
                    this.strokeWeight = strokeWidth
                    this.strokePosition = strokePosition.toJs()
                    this.visible = visible
                    this.zIndex = zIndex
                }
            )
            PolygonNode(polygon, mutableMapOf())
        },
        update = {
            set(points) { polygon.setOptions(jso { this.paths = points.toJsLatLngLiteralArray() }) }
            set(clickable) { polygon.setOptions(jso { this.clickable = clickable }) }
            set(draggable) { polygon.setOptions(jso { this.draggable = draggable }) }
            set(editable) { polygon.setOptions(jso { this.editable = editable }) }
            set(fillColor) { polygon.setOptions(jso { this.fillColor = fillColor }) }
            set(fillOpacity) { polygon.setOptions(jso { this.fillOpacity = fillOpacity }) }
            set(geodesic) { polygon.setOptions(jso { this.geodesic = geodesic }) }
            set(strokeColor) { polygon.setOptions(jso { this.strokeColor = strokeColor }) }
            set(strokeOpacity) { polygon.setOptions(jso { this.strokeOpacity = strokeOpacity }) }
            set(strokeWidth) { polygon.setOptions(jso { this.strokeWeight = strokeWidth }) }
            set(strokePosition) { polygon.setOptions(jso { this.strokePosition = strokePosition.toJs() }) }
            set(visible) { polygon.setOptions(jso { this.visible = visible }) }
            set(zIndex) { polygon.setOptions(jso { this.zIndex = zIndex }) }

            set(onClick) {
                val eventName = "click"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onClick((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onContextMenu) {
                val eventName = "contextmenu"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onContextMenu((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onDoubleClick) {
                val eventName = "dblclick"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onDoubleClick((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onDrag) {
                val eventName = "drag"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onDrag((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragEnd) {
                val eventName = "dragend"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onDragEnd((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragStart) {
                val eventName = "dragstart"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onDragStart((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseDown) {
                val eventName = "mousedown"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onMouseDown((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseMove) {
                val eventName = "mousemove"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onMouseMove((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseOut) {
                val eventName = "mouseout"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onMouseOut((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseOver) {
                val eventName = "mouseover"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onMouseOver((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseUp) {
                val eventName = "mouseup"
                events[eventName]?.remove()
                events[eventName] = polygon.addListener(eventName) { onMouseUp((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
        }
    )
}
