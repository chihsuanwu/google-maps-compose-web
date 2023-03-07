package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.JsCircle
import com.chihsuanwu.maps.compose.web.jsobject.newCircle
import com.chihsuanwu.maps.compose.web.jsobject.toJs
import com.chihsuanwu.maps.compose.web.jsobject.toLatLngJson
import js.core.jso

internal class CircleNode(
    val circle: JsCircle
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
 * @param editable If set to true, the user can edit this shape by dragging the control points
 * shown at the vertices and on each segment.
 * @param fillColor The fill color. All CSS3 colors are supported except for extended named colors.
 * @param fillOpacity The fill opacity between 0.0 and 1.0.
 * @param strokeColor The stroke color. All CSS3 colors are supported except for extended named colors.
 * @param strokeOpacity The stroke opacity between 0.0 and 1.0.
 * @param strokeWidth The stroke width in pixels.
 * @param strokePosition The stroke position. Defaults to CENTER.
 * @param visible Whether this circle is visible on the map.
 * @param zIndex The zIndex compared to other circles.
 */
@Composable
public fun Circle(
    center: LatLng,
    clickable: Boolean = false,
    draggable: Boolean = false,
    editable: Boolean = false,
    fillColor: String = "#000000",
    fillOpacity: Double = 1.0,
    radius: Double = 0.0,
    strokeColor: String = "#000000",
    strokeOpacity: Double = 1.0,
    strokeWidth: Int = 2,
    strokePosition: StrokePosition = StrokePosition.CENTER,
    visible: Boolean = true,
    zIndex: Double = 0.0,
    // TODO: add other properties
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<CircleNode, MapApplier>(
        factory = {
            val circle = newCircle(
                jso {
                    this.center = center.toLatLngJson()
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
            CircleNode(
                circle
            )
        },
        update = {
            set(center) { circle.setOptions(jso { this.center = center.toLatLngJson() }) }
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
        }
    )
}