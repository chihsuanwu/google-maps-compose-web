package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.JsPolyline
import com.chihsuanwu.maps.compose.web.jsobject.newPolyline
import com.chihsuanwu.maps.compose.web.jsobject.toLatLngJson
import js.core.jso

internal class PolylineNode(
    val polyline: JsPolyline,
) : MapNode {
    override fun onRemoved() {
        polyline.setMap(null)
    }
}


@Composable
public fun Polyline(
    points: List<LatLng>,
    color: String = "#000000",
    width: Int = 2,
    // TODO: add other properties
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<PolylineNode, MapApplier>(
        factory = {
            val polyline = newPolyline(
                jso {
                    this.path = points.map { it.toLatLngJson() }.toTypedArray()
                    this.strokeColor = color
                    this.strokeWeight = width
                }
            )
            polyline.setMap(mapApplier?.map)
            PolylineNode(
                polyline
            )
        },
        update = {
            set(points) {
                polyline.setOptions(
                    jso {
                        this.path = points.map { it.toLatLngJson() }.toTypedArray()
                    }
                )
            }
            set(color) {
                polyline.setOptions(
                    jso {
                        this.strokeColor = color
                    }
                )
            }
            set(width) {
                polyline.setOptions(
                    jso {
                        this.strokeWeight = width
                    }
                )
            }
        }
    )
}