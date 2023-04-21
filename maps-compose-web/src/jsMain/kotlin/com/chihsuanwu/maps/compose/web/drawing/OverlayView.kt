package com.chihsuanwu.maps.compose.web.drawing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.LatLng
import com.chihsuanwu.maps.compose.web.LatLngBounds
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.drawing.JSOverlayView
import com.chihsuanwu.maps.compose.web.jsobject.drawing.getPane
import com.chihsuanwu.maps.compose.web.jsobject.drawing.newOverlayView
import com.chihsuanwu.maps.compose.web.jsobject.toJsLatLngLiteral
import kotlinx.browser.document
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLDivElement


/**
 * The panes that are available to attach overlays.
 */
enum class MapPanes {
    /**
     * This pane contains the info window. It is above all map overlays. (Pane 4).
     */
    FloatPane,
    /**
     * This pane is the lowest pane and is above the tiles. It does not receive DOM events. (Pane 0).
     */
    MapPane,
    /**
     * This pane contains markers. It does not receive DOM events. (Pane 2).
     */
    MarkerLayer,
    /**
     * This pane contains polylines, polygons, ground overlays and tile layer overlays.
     * It does not receive DOM events. (Pane 1).
     */
    OverlayLayer,
    /**
     * This pane contains elements that receive DOM events. (Pane 3).
     */
    OverlayMouseTarget
}

internal class OverlayViewNode(
    val overlayView: JSOverlayView,
    val root: HTMLDivElement,
    var mapPane: MapPanes,
) : MapNode {

    override fun onRemoved() {
        overlayView.setMap(null)
    }
}

/**
 * A composable that can be used to display custom overlays on the map.
 *
 * @param bounds the bounds of the overlay
 * @param mapPane the pane to which the overlay is attached
 * @param content the DOM content of the overlay
 */
@Composable
fun OverlayView(
    bounds: LatLngBounds,
    mapPane: MapPanes = MapPanes.OverlayLayer,
    content: @Composable () -> Unit,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<OverlayViewNode, MapApplier>(
        factory = {
            val root = (document.createElement("div") as HTMLDivElement).apply {
                style.position = "absolute"
            }
            renderComposable(root) {
                content()
            }
            val overlayView = newOverlayView().apply {
                onAdd = { onAdd((js("this") as JSOverlayView), root, mapPane) }
                draw = { draw((js("this") as JSOverlayView), root, bounds) }
                onRemove = { root.parentNode?.removeChild(root) }
                setMap(mapApplier?.map)
            }
            OverlayViewNode(overlayView, root, mapPane)
        },
        update = {
            update(mapPane) {
                this.mapPane = it
                this.overlayView.apply {
                    onAdd = { onAdd((js("this") as JSOverlayView), root, it) }
                    // Trigger a reattach to the new pane
                    onRemove()
                    onAdd()
                }
            }
            update(bounds) {
                this.overlayView.draw = { draw((js("this") as JSOverlayView), root, it) }
                // Trigger a redraw
                this.overlayView.draw()
            }
        }
    )
}

private fun onAdd(view: JSOverlayView, root: HTMLDivElement, mapPane: MapPanes) {
    view.getPanes().getPane(mapPane).appendChild(root)
}

private fun draw(view: JSOverlayView, root: HTMLDivElement, bounds: LatLngBounds) {
    val overlayProjection = view.getProjection()
    val sw = overlayProjection.fromLatLngToDivPixel(LatLng(bounds.south, bounds.west).toJsLatLngLiteral())
    val ne = overlayProjection.fromLatLngToDivPixel(LatLng(bounds.north, bounds.east).toJsLatLngLiteral())
    root.style.apply {
        left = "${sw.x}px"
        top = "${ne.y}px"
        width = "${ne.x - sw.x}px"
        height = "${sw.y - ne.y}px"
    }
}
