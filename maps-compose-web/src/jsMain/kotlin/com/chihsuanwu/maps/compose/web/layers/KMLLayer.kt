package com.chihsuanwu.maps.compose.web.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.KMLMouseEvent
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.MapsEventListener
import com.chihsuanwu.maps.compose.web.jsobject.layers.JsKMLLayer
import com.chihsuanwu.maps.compose.web.jsobject.layers.JsKMLMouseEvent
import com.chihsuanwu.maps.compose.web.jsobject.layers.newKMLLayer
import com.chihsuanwu.maps.compose.web.jsobject.layers.toKMLMouseEvent
import js.core.jso

class KMLFeatureData(
    val author: KMLAuthor,
    val description: String,
    val id: String,
    val infoWindowHtml: String,
    val name: String,
    val snippet: String
)

class KMLAuthor(
    val email: String?,
    val name: String?,
    val uri: String?
)

internal class KMLLayerNode(
    val kmlLayer: JsKMLLayer,
    var events: MutableMap<String, MapsEventListener>
) : MapNode {
    override fun onRemoved() {
        kmlLayer.setMap(null)
    }
}

/**
 * A composable that adds a KML layer to the map.
 *
 * @param url The URL of the KML document to display.
 * @param clickable Whether the layer is clickable.
 * @param preserveViewport If this option is set to true or if the map's center and zoom were never set,
 * the input map is centered and zoomed to the bounding box of the contents of the layer.
 * @param screenOverlays Whether the layer should render screen overlays.
 * @param suppressInfoWindows Whether the layer should suppress info windows.
 * @param zIndex The zIndex of the layer.
 *
 * @param onClick Called when the user clicks on a feature.
 * @param onDefaultViewportChanged Called when the default viewport of the layer has changed.
 * @param onStatusChanged Called when the status of the layer has changed.
 */
@Composable
fun KMLLayer(
    url: String,
    clickable: Boolean = true,
    preserveViewport: Boolean = false,
    screenOverlays: Boolean = true,
    suppressInfoWindows: Boolean = false,
    zIndex: Double? = null,
    onClick: (KMLMouseEvent) -> Unit = {},
    onDefaultViewportChanged: () -> Unit = {},
    onStatusChanged: () -> Unit = {},
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<KMLLayerNode, MapApplier>(
        factory = {
            val layer = newKMLLayer(
                jso {
                    this.clickable = clickable
                    this.map = mapApplier?.map
                    this.preserveViewport = preserveViewport
                    this.screenOverlays = screenOverlays
                    this.suppressInfoWindows = suppressInfoWindows
                    this.url = url
                    this.zIndex = zIndex
                }
            )
            KMLLayerNode(layer, mutableMapOf())
        },
        update = {
            set(clickable) {
                kmlLayer.setOptions(jso { this.clickable = clickable })
            }
            set(preserveViewport) {
                kmlLayer.setOptions(jso { this.preserveViewport = preserveViewport })
            }
            set(screenOverlays) {
                kmlLayer.setOptions(jso { this.screenOverlays = screenOverlays })
            }
            set(suppressInfoWindows) {
                kmlLayer.setOptions(jso { this.suppressInfoWindows = suppressInfoWindows })
            }
            set(url) {
                kmlLayer.setOptions(jso { this.url = url })
            }
            set(zIndex) {
                kmlLayer.setOptions(jso { this.zIndex = zIndex })
            }
            set(onClick) {
                val eventName = "click"
                events[eventName]?.remove()
                events[eventName] = kmlLayer.addListener(eventName) { onClick((it as JsKMLMouseEvent).toKMLMouseEvent())  }
            }
            set(onDefaultViewportChanged) {
                val eventName = "defaultviewport_changed"
                events[eventName]?.remove()
                events[eventName] = kmlLayer.addListener(eventName) { onDefaultViewportChanged() }
            }
            set(onStatusChanged) {
                val eventName = "status_changed"
                events[eventName]?.remove()
                events[eventName] = kmlLayer.addListener(eventName) { onStatusChanged() }
            }
        }
    )
}