package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.JsPolyline
import com.chihsuanwu.maps.compose.web.jsobject.newPolyline
import com.chihsuanwu.maps.compose.web.jsobject.utils.toLatLngJsonArray
import js.core.jso

internal class PolylineNode(
    val polyline: JsPolyline
) : MapNode {
    override fun onRemoved() {
        polyline.setMap(null)
    }
}

/**
 * A composable for a polyline on the map.
 *
 * @param points The points of the polyline.
 * @param clickable Indicates whether this Polyline handles mouse events.
 * @param color The stroke color. All CSS3 colors are supported except for extended named colors.
 * @param draggable If set to true, the user can drag this shape over the map.
 * The geodesic property defines the mode of dragging.
 * @param editable If set to true, the user can edit this shape by dragging the control points
 * shown at the vertices and on each segment.
 * @param geodesic When true, edges of the polygon are interpreted as geodesic and
 * will follow the curvature of the Earth. When false, edges of the polygon are
 * rendered as straight lines in screen space.
 * @param opacity The opacity of the polyline between 0.0 and 1.0.
 * @param visible Whether this polyline is visible on the map.
 * @param width The width of the polyline in pixels.
 * @param zIndex The zIndex compared to other polys.
 *
 * @param events A map of event names to event handlers.
 * @param onClick A callback to be invoked when the polyline is clicked.
 */
@Composable
fun Polyline(
    points: List<LatLng>,
    clickable: Boolean = true,
    color: String = "#000000",
    draggable: Boolean = false,
    editable: Boolean = false,
    geodesic: Boolean = false,
    opacity: Double = 1.0,
    visible: Boolean = true,
    width: Int = 5,
    zIndex: Double? = null,
    // TODO: add other properties
    events: Map<String, (Any) -> Unit> = emptyMap(),
    onClick: (Any) -> Unit = {},
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<PolylineNode, MapApplier>(
        factory = {
            val polyline = newPolyline(
                jso {
                    this.path = points.toLatLngJsonArray()
                    this.clickable = clickable
                    this.strokeColor = color
                    this.draggable = draggable
                    this.editable = editable
                    this.geodesic = geodesic
                    this.map = mapApplier?.map
                    this.strokeOpacity = opacity
                    this.visible = visible
                    this.strokeWeight = width
                    this.zIndex = zIndex
                }
            )
            PolylineNode(polyline)
        },
        update = {
            set(points) { polyline.setOptions(jso { this.path = points.toLatLngJsonArray() }) }
            set(clickable) { polyline.setOptions(jso { this.clickable = clickable }) }
            set(color) { polyline.setOptions(jso { this.strokeColor = color }) }
            set(draggable) { polyline.setOptions(jso { this.draggable = draggable }) }
            set(editable) { polyline.setOptions(jso { this.editable = editable }) }
            set(geodesic) { polyline.setOptions(jso { this.geodesic = geodesic }) }
            set(opacity) { polyline.setOptions(jso { this.strokeOpacity = opacity }) }
            set(visible) { polyline.setOptions(jso { this.visible = visible }) }
            set(width) { polyline.setOptions(jso { this.strokeWeight = width }) }
            set(zIndex) { polyline.setOptions(jso { this.zIndex = zIndex }) }

            set(onClick) { polyline.addListener("click", onClick) }
            set(events) { events.forEach { (event, callback) -> polyline.addListener(event, callback) } }
        }
    )
}