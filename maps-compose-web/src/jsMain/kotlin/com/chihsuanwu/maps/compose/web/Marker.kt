package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.utils.toJsLatLngLiteral
import com.chihsuanwu.maps.compose.web.jsobject.utils.toLatLng
import com.chihsuanwu.maps.compose.web.jsobject.utils.toPointJson
import js.core.jso
import kotlinx.browser.document
import org.jetbrains.compose.web.renderComposable

internal class MarkerNode(
    val marker: JsMarker,
    val markerState: MarkerState,
    var events: List<MapsEventListener>,
    var onClick: MapsEventListener?,
    val infoContent: (@Composable () -> Unit)?
) : MapNode {
    override fun onAttached() {
        markerState.marker = marker
        markerState.infoContent = infoContent

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

    var infoContent: (@Composable () -> Unit)? = null

    private var infoWindow: InfoWindow? = null

    fun showInfoWindow() {
        if (infoWindow != null) return
//        val marker = marker ?: return
//        val infoContent = infoContent ?: return
        // Above code will cause compile error: Type inference failed. Expected type mismatch
        // Don't know why, following code works fine.:
        if (marker == null || infoContent == null) return
        val marker = marker!!
        val infoContent = infoContent!!

        val root = document.createElement("div")

        renderComposable(root) {
            infoContent()
        }

        infoWindow = newInfoWindow().apply {
            addListener("closeclick") { infoWindow = null }
            setContent(root)
            open(
                jso {
                    anchor = marker
                }
            )
        }
    }

    fun hideInfoWindow() {
        infoWindow?.close()
        infoWindow = null
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
 * @param draggable Indicates whether this marker can be dragged.
 * @param icon The icon for the foreground.
 * @param label The marker label.
 * @param opacity The marker's opacity between 0.0 and 1.0.
 * @param optimized Optimization enhances performance by rendering many markers as a single static element.
 * This is useful in cases where a large number of markers is required.
 * @param title Rollover text. If provided, an accessibility text will be added to the marker with the provided value.
 * @param visible Indicates whether this marker is visible.
 * @param zIndex All markers are displayed on the map in order of their zIndex,
 * with higher values displaying in front of markers with lower values.
 *
 * @param events the events to be applied to the marker.
 * @param onClick A callback that will be invoked when the marker is clicked.
 */
@Composable
fun Marker(
    state: MarkerState = rememberMarkerState(),
    anchor: Point? = null,
    clickable: Boolean = true,
    crossOnDrag: Boolean = true,
    // TODO: cursor
    draggable: Boolean = false,
    icon: String? = null, // TODO: Icon/Symbol
    label: String? = null, // TODO: MarkerLabel
    opacity: Double = 1.0,
    optimized: Boolean? = null,
    // TODO: shape
    title: String? = null,
    visible: Boolean = true,
    zIndex: Double? = null,
    events: EventsBuilder.() -> Unit = {},
    onClick: (MouseEvent) -> Unit = {},
    infoContent: (@Composable () -> Unit)? = null,
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
        onClick = onClick,
        infoContent = infoContent
    )
}


@Composable
private fun MarkerImpl(
    state: MarkerState = rememberMarkerState(),
    anchor: Point? = null,
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
    events: EventsBuilder.() -> Unit = {},
    onClick: (MouseEvent) -> Unit = {},
    infoContent: (@Composable () -> Unit)? = null,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<MarkerNode, MapApplier>(
        factory = {
            val marker = newMarker(
                jso {
                    this.position = state.position.toJsLatLngLiteral()
                    this.anchorPoint = anchor?.toPointJson()
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
                marker = marker,
                markerState = state,
                events = emptyList(),
                onClick = null,
                infoContent = infoContent,
            )
        },
        update = {
            set(state.position) { marker.setOptions(jso { this.position = state.position.toJsLatLngLiteral() }) }
            set(anchor) {
                anchor?.let { marker.setOptions(jso { this.anchorPoint = it.toPointJson() }) }
            }
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
                this.events.forEach { it.remove() }
                this.events = EventsBuilder().apply(events).build().map { e ->
                    when (e) {
                        is Event.Unit -> marker.addListener(e.event) { e.callback(it) }
                        is Event.Mouse -> marker.addListener(e.event) { e.callback((it as MapMouseEvent).toMouseEvent()) }
                    }
                }
            }
            set(onClick) {
                this.onClick?.remove()
                this.onClick = marker.addListener("click") { onClick((it as MapMouseEvent).toMouseEvent()) }
            }
        }
    )
}