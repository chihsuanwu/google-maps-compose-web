package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.*
import js.core.jso

/**
 * [google.maps.IconSequence](https://developers.google.com/maps/documentation/javascript/reference/polygon#IconSequence)
 *
 * @param fixedRotation If true, each icon in the sequence will be rotated to match the angle of the edge.
 * @param icon The icon to render.
 * @param offset The distance from the start of the line at which an icon is to be rendered.
 * This distance may be expressed as a percentage of line's length (e.g. '50%') or in pixels (e.g. '50px').
 * @param repeat The distance between consecutive icons on the line.
 * This distance may be expressed as a percentage of the line's length (e.g. '50%') or in pixels (e.g. '50px').
 * To disable repeating of the icon, specify '0'.
 */
data class IconSequence(
    val fixedRotation: Boolean? = null,
    val icon: MarkerIcon.Symbol? = null,
    val offset: String? = "100%",
    val repeat: String? = "0"
)

internal class PolylineNode(
    val polyline: JsPolyline,
    var events: MutableMap<String, MapsEventListener>,
) : MapNode {
    override fun onRemoved() {
        polyline.setMap(null)
    }
}

/**
 * A composable for a polyline on the map.
 *
 * @param points The points of the polyline.
 *
 * @param clickable Indicates whether this Polyline handles mouse events.
 * @param color The stroke color. All CSS3 colors are supported except for extended named colors.
 * @param draggable If set to true, the user can drag this shape over the map.
 * The geodesic property defines the mode of dragging.
 * @param editable If set to true, the user can edit this shape by dragging the control points
 * shown at the vertices and on each segment.
 * @param geodesic When true, edges of the polygon are interpreted as geodesic and
 * will follow the curvature of the Earth. When false, edges of the polygon are
 * rendered as straight lines in screen space.
 * @param icons The icons to be rendered along the polyline.
 * @param opacity The opacity of the polyline between 0.0 and 1.0.
 * @param visible Whether this polyline is visible on the map.
 * @param width The width of the polyline in pixels.
 * @param zIndex The zIndex compared to other polys.
 *
 * @param onClick A callback to be invoked when the polyline is clicked.
 * @param onContextMenu A callback to be invoked when the DOM contextmenu event is fired on the polyline.
 * @param onDoubleClick A callback to be invoked when the polyline is double-clicked.
 * @param onDrag A callback to be invoked repeatedly while the user drags the polyline.
 * @param onDragEnd A callback to be invoked when the user stops dragging the polyline.
 * @param onDragStart A callback to be invoked when the user starts dragging the polyline.
 * @param onMouseDown A callback to be invoked when the DOM mousedown event is fired on the polyline.
 * @param onMouseMove A callback to be invoked when the DOM mousemove event is fired on the polyline.
 * @param onMouseOut A callback to be invoked when the mouseout event is fired.
 * @param onMouseOver A callback to be invoked when the mouseover event is fired.
 * @param onMouseUp A callback to be invoked when the DOM mouseup event is fired on the polyline.
 *
 */
@Composable
fun Polyline(
    points: List<LatLng>,
    clickable: Boolean = true,
    color: String = "#000000",
    draggable: Boolean = false,
    editable: Boolean = false,
    geodesic: Boolean = false,
    icons: List<IconSequence>? = null,
    opacity: Double = 1.0,
    visible: Boolean = true,
    width: Int = 5,
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
    ComposeNode<PolylineNode, MapApplier>(
        factory = {
            val polyline = newPolyline(
                jso {
                    this.path = points.toJsLatLngLiteralArray()
                    this.clickable = clickable
                    this.strokeColor = color
                    this.draggable = draggable
                    this.editable = editable
                    this.geodesic = geodesic
                    this.icons = icons?.toJsIconSequenceArray()
                    this.map = mapApplier?.map
                    this.strokeOpacity = opacity
                    this.visible = visible
                    this.strokeWeight = width
                    this.zIndex = zIndex
                }
            )
            PolylineNode(polyline, mutableMapOf())
        },
        update = {
            set(points) { polyline.setOptions(jso { this.path = points.toJsLatLngLiteralArray() }) }
            set(clickable) { polyline.setOptions(jso { this.clickable = clickable }) }
            set(color) { polyline.setOptions(jso { this.strokeColor = color }) }
            set(draggable) { polyline.setOptions(jso { this.draggable = draggable }) }
            set(editable) { polyline.setOptions(jso { this.editable = editable }) }
            set(geodesic) { polyline.setOptions(jso { this.geodesic = geodesic }) }
            set(icons) { polyline.setOptions(jso { this.icons = icons?.toJsIconSequenceArray() }) }
            set(opacity) { polyline.setOptions(jso { this.strokeOpacity = opacity }) }
            set(visible) { polyline.setOptions(jso { this.visible = visible }) }
            set(width) { polyline.setOptions(jso { this.strokeWeight = width }) }
            set(zIndex) { polyline.setOptions(jso { this.zIndex = zIndex }) }

            set(onClick) {
                val eventName = "click"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onClick((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onContextMenu) {
                val eventName = "contextmenu"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onContextMenu((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onDoubleClick) {
                val eventName = "dblclick"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onDoubleClick((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onDrag) {
                val eventName = "drag"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onDrag((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragEnd) {
                val eventName = "dragend"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onDragEnd((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragStart) {
                val eventName = "dragstart"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onDragStart((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseDown) {
                val eventName = "mousedown"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onMouseDown((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseMove) {
                val eventName = "mousemove"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onMouseMove((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseOut) {
                val eventName = "mouseout"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onMouseOut((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseOver) {
                val eventName = "mouseover"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onMouseOver((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
            set(onMouseUp) {
                val eventName = "mouseup"
                events[eventName]?.remove()
                events[eventName] = polyline.addListener(eventName) { onMouseUp((it as JsPolyMouseEvent).toPolyMouseEvent()) }
            }
        }
    )
}