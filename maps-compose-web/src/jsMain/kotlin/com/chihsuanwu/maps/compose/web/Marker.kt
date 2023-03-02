package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.jsobject.JsMarker
import com.chihsuanwu.maps.compose.web.jsobject.newMarker
import com.chihsuanwu.maps.compose.web.jsobject.toLatLngJson
import js.core.jso

internal class MarkerNode(
    val marker: JsMarker,
    val markerState: MarkerState,
) : MapNode {
    override fun onAttached() {
        markerState.marker = marker
    }

    override fun onRemoved() {
        markerState.marker = null
        marker.setMap(null)
    }

    override fun onCleared() {
        markerState.marker = null
        marker.setMap(null)
    }
}


/**
 * A state object that can be hoisted to control and observe the marker state.
 *
 * @param position the initial marker position
 */
public class MarkerState(
    position: LatLng = LatLng(0.0, 0.0)
) {
    /**
     * Current position of the marker.
     */
    public var position: LatLng by mutableStateOf(position)

    // The marker associated with this MarkerState.
    private val markerState: MutableState<JsMarker?> = mutableStateOf(null)
    internal var marker: JsMarker?
        get() = markerState.value
        set(value) {
            if (markerState.value == null && value == null) return
            if (markerState.value != null && value != null) {
                error("MarkerState may only be associated with one Marker at a time.")
            }
            markerState.value = value
        }
}

@Composable
public fun rememberMarkerState(
    key: String? = null,
    position: LatLng = LatLng(0.0, 0.0)
): MarkerState = remember(key1 = key) {
    MarkerState(position)
}


@Composable
public fun Marker(
    state: MarkerState,
    icon: String? = null,
    title: String? = null,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<MarkerNode, MapApplier>(
        factory = {
            val marker = newMarker(
                jso {
                    this.position = state.position.toLatLngJson()
                    this.icon = icon
                    this.title = title
                }
            )
            marker.setMap(mapApplier?.map)
            MarkerNode(
                marker,
                state
            )
        },
        update = {
            set(state.position) {
                marker.setOptions(
                    jso {
                        this.position = state.position.toLatLngJson()
                    }
                )
            }
            set(icon) {
                marker.setOptions(
                    jso {
                        this.icon = icon
                    }
                )
            }
            set(title) {
                marker.setOptions(
                    jso {
                        this.title = title
                    }
                )
            }
        }
    )
}

