package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.JsPolygon
import com.chihsuanwu.maps.compose.web.jsobject.newPolyline
import com.chihsuanwu.maps.compose.web.jsobject.toLatLngJson
import js.core.jso

internal class PolygonNode(
    val polygon: JsPolygon,
) : MapNode {
    override fun onRemoved() {
        polygon.setMap(null)
    }
}


@Composable
public fun Polygon(
    points: List<LatLng>,
    fillColor: String = "#000000",
    strokeColor: String = "#000000",
    strokeWidth: Int = 2,
    // TODO: add other properties
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<PolygonNode, MapApplier>(
        factory = {
            val polygon = newPolygon(
                jso {
                    this.path = points.map { it.toLatLngJson() }.toTypedArray()
                    this.fillColor = fillColor
                    this.strokeColor = strokeColor
                    this.strokeWeight = strokeWidth
                }
            )
            polygon.setMap(mapApplier?.map)
            PolygonNode(
                polygon
            )
        },
        update = {
            set(points) {
                polygon.setOptions(
                    jso {
                        this.path = points.map { it.toLatLngJson() }.toTypedArray()
                    }
                )
            }
            set(fillColor) {
                polygon.setOptions(
                    jso {
                        this.fillColor = fillColor
                    }
                )
            }
            set(strokeColor) {
                polygon.setOptions(
                    jso {
                        this.strokeColor = strokeColor
                    }
                )
            }
            set(strokeWidth) {
                polygon.setOptions(
                    jso {
                        this.strokeWeight = strokeWidth
                    }
                )
            }
        }
    )
}