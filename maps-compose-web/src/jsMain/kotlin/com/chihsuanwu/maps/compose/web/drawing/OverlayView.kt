package com.chihsuanwu.maps.compose.web.drawing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.*
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.drawing.JSOverlayView
import com.chihsuanwu.maps.compose.web.jsobject.drawing.newOverlayView
import com.chihsuanwu.maps.compose.web.jsobject.toJsLatLngLiteral
import com.chihsuanwu.maps.compose.web.jsobject.toJsPoint
import kotlinx.browser.document
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLDivElement
import org.w3c.dom.get

enum class MapPanes {
    FloatPane,
    MapPane,
    MarkerLayer,
    OverlayLayer,
    OverlayMouseTarget
}

internal class OverlayViewNode(
    val overlayView: JSOverlayView,
    val map: MapView?,
) : MapNode {
    override fun onAttached() {
        overlayView.setMap(map)
    }

    override fun onRemoved() {
        overlayView.setMap(null)
    }
}


@Composable
fun OverlayView(
    bounds: LatLngBounds,
    mapPane: MapPanes = MapPanes.OverlayLayer,
    content: @Composable () -> Unit,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<OverlayViewNode, MapApplier>(
        factory = {
            val root = document.createElement("div") as HTMLDivElement
            root.style.position = "absolute"
            renderComposable(root) {
                content()
            }
            val onAdd: () -> Unit = {
                val overlayView = js("this") as JSOverlayView
                overlayView.getPanes().overlayLayer.appendChild(root)
            }
            val draw: () -> Unit = {
                val overlayView = js("this") as JSOverlayView
                val overlayProjection = overlayView.getProjection()
                val sw = overlayProjection.fromLatLngToDivPixel(LatLng(bounds.south, bounds.west).toJsLatLngLiteral())
                val ne = overlayProjection.fromLatLngToDivPixel(LatLng(bounds.north, bounds.east).toJsLatLngLiteral())
                root.style.left = "${sw.x}px"
                root.style.top = "${ne.y}px"
                root.style.width = "${ne.x - sw.x}px"
                root.style.height = "${sw.y - ne.y}px"
            }
            val onRemove: () -> Unit = {
                val overlayView = js("this") as JSOverlayView
                overlayView.getPanes().overlayLayer.removeChild(root)
            }
            val overlayView = newOverlayView(onAdd, draw, onRemove)

            OverlayViewNode(overlayView, mapApplier?.map)
        },
        update = {

        }
    )
}