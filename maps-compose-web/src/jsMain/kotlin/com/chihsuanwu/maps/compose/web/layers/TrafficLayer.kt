package com.chihsuanwu.maps.compose.web.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.layers.JsTrafficLayer
import com.chihsuanwu.maps.compose.web.jsobject.layers.newTrafficLayer
import js.core.jso


internal class TrafficLayerNode(
    val trafficLayer: JsTrafficLayer,
    val map: MapView?,
) : MapNode {
    override fun onRemoved() {
        trafficLayer.setMap(null)
    }
}

/**
 * A composable that adds a traffic layer to the map.
 *
 * @param autoRefresh Whether the layer will update automatically.
 */
@Composable
fun TrafficLayer(
    autoRefresh: Boolean = true,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<TrafficLayerNode, MapApplier>(
        factory = {
            val layer = newTrafficLayer(
                jso {
                    this.autoRefresh = autoRefresh
                    this.map = mapApplier?.map
                }
            )
            TrafficLayerNode(layer, mapApplier?.map)
        },
        update = {
            set(autoRefresh) {
                trafficLayer.setOptions(jso { this.autoRefresh = autoRefresh })
            }
        }
    )
}
