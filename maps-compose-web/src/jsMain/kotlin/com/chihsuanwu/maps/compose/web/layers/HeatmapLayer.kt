package com.chihsuanwu.maps.compose.web.layers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.GoogleMap
import com.chihsuanwu.maps.compose.web.LatLng
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.layers.*
import com.chihsuanwu.maps.compose.web.jsobject.layers.JsHeatmapLayer
import com.chihsuanwu.maps.compose.web.jsobject.layers.newHeatmapLayer
import js.core.jso


internal class HeatmapLayerNode(
    val heatmapLayer: JsHeatmapLayer,
    val map: MapView?,
) : MapNode {
    override fun onRemoved() {
        heatmapLayer.setMap(null)
    }
}

/**
 * A composable that adds a heatmap layer to the map.
 * Note that the heatmap layer is part of the visualization library. You must include the visualization library in your
 * by adding 'libraries=visualization' to the 'extra' parameter of the [GoogleMap] composable.
 *
 * @param data The data points to display.
 * @param dissipating Whether heatmaps dissipate on zoom. By default, the radius of influence of a data point
 * is specified by the radius option only.
 * @param gradient The color gradient of the heatmap, specified as an array of CSS color strings.
 * @param maxIntensity The maximum intensity of the heatmap.
 * @param opacity The opacity of the heatmap. Defaults to 0.6.
 * @param radius The radius of influence for each data point, in pixels.
 */
@Composable
fun HeatmapLayer(
    data: List<WeightedLocation>,
    dissipating: Boolean = true,
    gradient: List<String>? = null,
    maxIntensity: Int? = null,
    opacity: Double = 0.6,
    radius: Int? = null,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<HeatmapLayerNode, MapApplier>(
        factory = {
            val layer = newHeatmapLayer(
                jso {
                    this.data = data.map { it.toJsWeightedLocation() }.toTypedArray()
                    this.dissipating = dissipating
                    this.gradient = gradient?.toTypedArray()
                    this.map = mapApplier?.map
                    this.maxIntensity = maxIntensity
                    this.opacity = opacity
                    this.radius = radius
                }
            )
            HeatmapLayerNode(layer, mapApplier?.map)
        },
        update = {
            set(data) {
                heatmapLayer.setOptions(jso { this.data = data.map { it.toJsWeightedLocation() }.toTypedArray() })
            }
            set(dissipating) {
                heatmapLayer.setOptions(jso { this.dissipating = dissipating })
            }
            set(gradient) {
                heatmapLayer.setOptions(jso { this.gradient = gradient?.toTypedArray() })
            }
            set(maxIntensity) {
                heatmapLayer.setOptions(jso { this.maxIntensity = maxIntensity })
            }
            set(opacity) {
                heatmapLayer.setOptions(jso { this.opacity = opacity })
            }
            set(radius) {
                heatmapLayer.setOptions(jso { this.radius = radius })
            }
        }
    )
}

class WeightedLocation(
    val location: LatLng,
    val weight: Double = 1.0,
)