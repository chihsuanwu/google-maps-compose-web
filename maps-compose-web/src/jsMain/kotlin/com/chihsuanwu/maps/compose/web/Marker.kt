package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.jsobject.JsMarker
import com.chihsuanwu.maps.compose.web.jsobject.newMarker
import com.chihsuanwu.maps.compose.web.jsobject.utils.toLatLng
import com.chihsuanwu.maps.compose.web.jsobject.utils.toLatLngJson
import com.chihsuanwu.maps.compose.web.jsobject.utils.toPointJson
import js.core.jso
import org.w3c.workers.Client

internal class MarkerNode(
    val marker: JsMarker,
    val markerState: MarkerState,
) : MapNode {
    override fun onAttached() {
        markerState.marker = marker
        marker.addListener("dragend") {
            markerState.position = marker.getPosition().toLatLng()
        }
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
class MarkerState(
    position: LatLng = LatLng(0.0, 0.0)
) {
    /**
     * Current position of the marker.
     */
    var position: LatLng by mutableStateOf(position)

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
fun rememberMarkerState(
    key: String? = null,
    position: LatLng = LatLng(0.0, 0.0)
): MarkerState = remember(key1 = key) {
    MarkerState(position)
}


/**
 * A composable for a marker on the map.
 *
 * @param state the [MarkerState] object to be used to control and observe the marker state.
 * @param anchor the anchor for the marker icon.
 * @param clickable Indicates whether this marker handles mouse events.
 * @param crossOnDrag If false, disables cross that appears beneath the marker when dragging.
 */
@Composable
fun Marker(
    state: MarkerState = rememberMarkerState(),
    anchor: Point = Point(0.5, 1.0),
    clickable: Boolean = true,
    crossOnDrag: Boolean = true,
    // TODO: cursor
    draggable: Boolean = false,
    icon: String? = null,
    label: String? = null,
    opacity: Double = 1.0,
    optimized: Boolean? = null,
    // TODO: shape
    title: String? = null,
    visible: Boolean = true,
    zIndex: Double? = null,
    // TODO: add other properties
    events: Map<String, (Any) -> Unit> = emptyMap(),
    onClick: (Any) -> Unit = {},
) {
    MarkerImpl(
        state = state,
        anchor = anchor,
        clickable = clickable,
        crossOnDrag = crossOnDrag,
        draggable = draggable,
        icon = icon,
        label = label,
        opacity = opacity,
        optimized = optimized,
        title = title,
        visible = visible,
        zIndex = zIndex,
        events = events,
        onClick = onClick
    )
}


@Composable
private fun MarkerImpl(
    state: MarkerState = rememberMarkerState(),
    anchor: Point = Point(0.5, 1.0),
    clickable: Boolean = true,
    crossOnDrag: Boolean = true,
    // TODO: cursor
    draggable: Boolean = false,
    icon: String? = null,
    label: String? = null,
    opacity: Double = 1.0,
    optimized: Boolean? = null,
    // TODO: shape
    title: String? = null,
    visible: Boolean = true,
    zIndex: Double? = null,
    // TODO: add other properties
    events: Map<String, (Any) -> Unit> = emptyMap(),
    onClick: (Any) -> Unit = {},
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<MarkerNode, MapApplier>(
        factory = {
            val marker = newMarker(
                jso {
                    this.position = state.position.toLatLngJson()
                    this.anchorPoint = anchor.toPointJson()
                    this.clickable = clickable
                    this.crossOnDrag = crossOnDrag
                    this.draggable = draggable
                    this.icon = icon
                    this.label = label
                    this.map = mapApplier?.map
                    this.opacity = opacity
                    this.optimized = optimized
                    this.title = title
                    this.visible = visible
                    this.zIndex = zIndex
                }
            )
            MarkerNode(
                marker,
                state
            )
        },
        update = {
            set(state.position) { marker.setOptions(jso { this.position = state.position.toLatLngJson() }) }
            set(anchor) { marker.setOptions(jso { this.anchorPoint = anchor.toPointJson() }) }
            set(clickable) { marker.setOptions(jso { this.clickable = clickable }) }
            set(crossOnDrag) { marker.setOptions(jso { this.crossOnDrag = crossOnDrag }) }
            set(draggable) { marker.setOptions(jso { this.draggable = draggable }) }
            set(icon) { marker.setOptions(jso { this.icon = icon }) }
            set(label) { marker.setOptions(jso { this.label = label }) }
            set(opacity) { marker.setOptions(jso { this.opacity = opacity }) }
            set(optimized) { marker.setOptions(jso { this.optimized = optimized }) }
            set(title) { marker.setOptions(jso { this.title = title }) }
            set(visible) { marker.setOptions(jso { this.visible = visible }) }
            set(zIndex) { marker.setOptions(jso { this.zIndex = zIndex }) }

            set(events) {
                events.forEach { (event, listener) ->
                    marker.addListener(event) { listener(it) }
                }
            }
            set(onClick) { marker.addListener("click") { onClick(it) } }
        }
    )
}