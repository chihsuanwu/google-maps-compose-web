package com.chihsuanwu.maps.compose.web.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.layers.JsTransitLayer
import com.chihsuanwu.maps.compose.web.jsobject.layers.newTransitLayer


internal class TransitLayerNode(
    val transitLayer: JsTransitLayer,
) : MapNode {
    override fun onRemoved() {
        transitLayer.setMap(null)
    }
}

/**
 * A composable that adds a transit layer to the map.
 */
@Composable
fun TransitLayer() {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<TransitLayerNode, MapApplier>(
        factory = {
            val layer = newTransitLayer().apply {
                setMap(mapApplier?.map)
            }
            TransitLayerNode(layer)
        },
        update = {}
    )
}
