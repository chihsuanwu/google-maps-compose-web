package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.toJsLatLngLiteral
import js.core.jso
import kotlinx.browser.document
import org.jetbrains.compose.web.renderComposable


internal class InfoWindowNode(
    val infoWindow: JsInfoWindow,
    val infoWindowState: InfoWindowState,
    val map: MapView?,
    var events: MutableMap<String, MapsEventListener>,
) : MapNode {
    override fun onAttached() {
        infoWindowState.infoWindow = infoWindow
        infoWindowState.map = map
    }

    override fun onRemoved() {
        infoWindowState.infoWindow = null
        infoWindow.close()
    }

    override fun onCleared() {
        infoWindowState.infoWindow = null
        infoWindow.close()
    }
}


/**
 * A state object that can be hoisted to control and observe the info window state.
 *
 * @param position the initial info window position
 */
class InfoWindowState(
    position: LatLng = LatLng(0.0, 0.0)
) {
    var position: LatLng by mutableStateOf(position)

    internal var infoWindow: JsInfoWindow? = null
    internal var map: MapView? = null

    fun showInfoWindow() {
//        val map = map ?: return
        // Above code will cause compile error: Type inference failed. Expected type mismatch
        // Don't know why, following code works fine.:
        if (map == null) return
        val map = map!!

        infoWindow?.open(
            jso {
                this.map = map
            }
        )
    }

    fun hideInfoWindow() {
        infoWindow?.close()
    }
}

/**
 * A composable that represents a Google Maps InfoWindow.
 *
 * @param state The [InfoWindowState] object that controls and observes the state of the InfoWindow.
 * @param ariaLabel AriaLabel to assign to the InfoWindow.
 * @param disableAutoPan Disable panning the map to make the InfoWindow fully visible when it opens.
 * @param maxWidth Maximum width of the InfoWindow, in pixels.
 * @param minWidth Minimum width of the InfoWindow, in pixels.
 * @param pixelOffset Offset, in pixels, of the tip of the InfoWindow from the point on the map
 * at whose geographical coordinates the InfoWindow is anchored.
 * @param zIndex The zIndex compared to other windows.
 *
 *
 *
 * @param content The content of the InfoWindow.
 */
@Composable
fun InfoWindow(
    state: InfoWindowState,
    ariaLabel: String? = null,
    disableAutoPan: Boolean = false,
    maxWidth: Int? = null,
    minWidth: Int? = null,
    pixelOffset: Size? = null,
    zIndex: Double? = null,
    onCloseClick: () -> Unit = {},
    onContentChanged: () -> Unit = {},
    onDOMReady: () -> Unit = {},
    onPositionChanged: () -> Unit = {},
    onVisible: () -> Unit = {},
    onZIndexChanged: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<InfoWindowNode, MapApplier>(
        factory = {
            val root = document.createElement("div")
            renderComposable(root) {
                content()
            }
            val infoWindow = newInfoWindow(
                jso {
                    this.position = state.position.toJsLatLngLiteral()
                    this.ariaLabel = ariaLabel
                    this.disableAutoPan = disableAutoPan
                    this.maxWidth = maxWidth
                    this.minWidth = minWidth
                    this.pixelOffset = pixelOffset?.toJsSize()
                    this.zIndex = zIndex
                }
            )
            infoWindow.setContent(root)
            InfoWindowNode(infoWindow, state, mapApplier?.map, mutableMapOf())
        },
        update = {
            set(state.position) { infoWindow.setOptions(jso { this.position = state.position.toJsLatLngLiteral() }) }
            set(ariaLabel) { infoWindow.setOptions(jso { this.ariaLabel = ariaLabel }) }
            set(disableAutoPan) { infoWindow.setOptions(jso { this.disableAutoPan = disableAutoPan }) }
            set(maxWidth) { infoWindow.setOptions(jso { this.maxWidth = maxWidth }) }
            set(minWidth) { infoWindow.setOptions(jso { this.minWidth = minWidth }) }
            set(pixelOffset) { infoWindow.setOptions(jso { this.pixelOffset = pixelOffset?.toJsSize() }) }
            set(zIndex) { infoWindow.setOptions(jso { this.zIndex = zIndex }) }

            set(onCloseClick) {
                val eventName = "closeclick"
                events[eventName]?.remove()
                events[eventName] = infoWindow.addListener(eventName) { onCloseClick() }
            }
            set(onContentChanged) {
                val eventName = "content_changed"
                events[eventName]?.remove()
                events[eventName] = infoWindow.addListener(eventName) { onContentChanged() }
            }
            set(onDOMReady) {
                val eventName = "domready"
                events[eventName]?.remove()
                events[eventName] = infoWindow.addListener(eventName) { onDOMReady() }
            }
            set(onPositionChanged) {
                val eventName = "position_changed"
                events[eventName]?.remove()
                events[eventName] = infoWindow.addListener(eventName) { onPositionChanged() }
            }
            set(onVisible) {
                val eventName = "visible"
                events[eventName]?.remove()
                events[eventName] = infoWindow.addListener(eventName) { onVisible() }
            }
            set(onZIndexChanged) {
                val eventName = "zindex_changed"
                events[eventName]?.remove()
                events[eventName] = infoWindow.addListener(eventName) { onZIndexChanged() }
            }
        }
    )
}