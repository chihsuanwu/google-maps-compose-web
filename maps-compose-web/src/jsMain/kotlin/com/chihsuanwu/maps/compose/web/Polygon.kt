package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.utils.toJs
import com.chihsuanwu.maps.compose.web.jsobject.utils.toJsLatLngLiteralArray
import js.core.jso

internal class PolygonNode(
    val polygon: JsPolygon,
    var events: List<MapsEventListener>,
    var onClick: MapsEventListener?,
) : MapNode {
    override fun onRemoved() {
        polygon.setMap(null)
    }
}

/**
 * A composable for a polygon on the map.
 *
 * @param points The points of the polygon.
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
 * @param events The events to be applied to the polygon.
 * @param onClick A callback to be invoked when the polygon is clicked.
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
    events: EventsBuilder.() -> Unit = {},
    onClick: (MouseEvent) -> Unit = {},
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
            PolygonNode(polygon, emptyList(), null)
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

            set(events) {
                this.events.forEach { it.remove() }
                this.events = EventsBuilder().apply(events).build().map { e ->
                    when (e) {
                        is Event.Unit -> polygon.addListener(e.event) { e.callback(it) }
                        is Event.Mouse -> polygon.addListener(e.event) { e.callback((it as MapMouseEvent).toMouseEvent()) }
                    }
                }
            }
            set(onClick) {
                this.onClick?.remove()
                this.onClick = polygon.addListener("click") { onClick((it as MapMouseEvent).toMouseEvent()) }
            }
        }
    )
}
