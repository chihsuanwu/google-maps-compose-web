package com.chihsuanwu.maps.compose.web.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.layers.JsBicyclingLayer
import com.chihsuanwu.maps.compose.web.jsobject.layers.newBicyclingLayer

internal class BicyclingLayerNode(
    val bicyclingLayer: JsBicyclingLayer,
    val map: MapView?,
) : MapNode {
    override fun onRemoved() {
        bicyclingLayer.setMap(null)
    }
}

/**
 * A composable that adds a bicycling layer to the map.
 */
@Composable
fun BicyclingLayer() {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<BicyclingLayerNode, MapApplier>(
        factory = {
            val layer = newBicyclingLayer().apply {
                setMap(mapApplier?.map)
            }
            BicyclingLayerNode(layer, mapApplier?.map)
        },
        update = {}
    )
}
