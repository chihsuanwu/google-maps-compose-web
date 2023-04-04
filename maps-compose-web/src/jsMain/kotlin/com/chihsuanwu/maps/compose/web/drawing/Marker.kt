package com.chihsuanwu.maps.compose.web.drawing

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.*
import com.chihsuanwu.maps.compose.web.MapApplier
import com.chihsuanwu.maps.compose.web.MapNode
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.drawing.*
import com.chihsuanwu.maps.compose.web.jsobject.drawing.JsMarker
import com.chihsuanwu.maps.compose.web.jsobject.drawing.newMarker
import com.chihsuanwu.maps.compose.web.jsobject.drawing.toJsAnimation
import com.chihsuanwu.maps.compose.web.jsobject.drawing.toJsMarkerIcon
import js.core.jso
import kotlinx.browser.document
import org.jetbrains.compose.web.renderComposable

/**
 * Icon for a marker.
 * Can be [URL], [Icon] or [Symbol].
 */
sealed interface MarkerIcon {
    /**
     * An Icon with the string as url.
     */
    class URL(val url: String) : MarkerIcon

    /**
     * A [google.maps.Icon](https://developers.google.com/maps/documentation/javascript/reference/marker#Icon)
     * object.
     */
    class Icon(
        val url: String,
        val anchor: Point? = null,
        val labelOrigin: Point? = null,
        val origin: Point? = null,
        val scaledSize: Size? = null,
        val size: Size? = null,
    ) : MarkerIcon

    /**
     * A [google.maps.Symbol](https://developers.google.com/maps/documentation/javascript/reference/marker#Symbol)
     * object.
     */
    class Symbol(
        val path: Path,
        val anchor: Point? = null,
        val fillColor: String? = null,
        val fillOpacity: Double? = null,
        val labelOrigin: Point? = null,
        val rotation: Double? = null,
        val scale: Double? = null,
        val strokeColor: String? = null,
        val strokeOpacity: Double? = null,
        val strokeWeight: Double? = null,
    ) : MarkerIcon {
        sealed interface Path {
            class StringPath(val path: String) : Path
            /**
             * [google.maps.SymbolPath](https://developers.google.com/maps/documentation/javascript/reference/marker#SymbolPath)
             */
            enum class SymbolPath : Path {
                BackwardClosedArrow,
                BackwardOpenArrow,
                Circle,
                ForwardClosedArrow,
                ForwardOpenArrow,
            }
        }
    }
}

/**
 * A marker label is a letter or number that appears inside a marker.
 * Can be [Text] or [Label].
 */
sealed interface MarkerLabel {
    class Text(val text: String) : MarkerLabel

    /**
     * A [google.maps.MarkerLabel](https://developers.google.com/maps/documentation/javascript/reference/marker#MarkerLabel)
     * object.
     */
    class Label(
        val text: String,
        val className: String? = null,
        val color: String? = null,
        val fontFamily: String? = null,
        val fontSize: String? = null,
        val fontWeight: String? = null,
    ) : MarkerLabel
}

class MarkerShape(
    val coords: List<Int>,
    val type: Type,
) {
    enum class Type { Circle, Poly, Rect, }
}

enum class MarkerAnimation {
    BOUNCE,
    DROP,
}

internal class MarkerNode(
    val marker: JsMarker,
    val markerState: MarkerState,
    var events: MutableMap<String, MapsEventListener>,
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

    internal var infoContent: (@Composable () -> Unit)? = null

    private var infoWindow: JsInfoWindow? = null

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

        infoWindow = newInfoWindow(jso {}).apply {
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
 * @param label A marker label is a letter or number that appears inside a marker.
 * @param opacity The marker's opacity between 0.0 and 1.0.
 * @param optimized Optimization enhances performance by rendering many markers as a single static element.
 * This is useful in cases where a large number of markers is required.
 * @param title Rollover text. If provided, an accessibility text will be added to the marker with the provided value.
 * @param visible Indicates whether this marker is visible.
 * @param zIndex All markers are displayed on the map in order of their zIndex,
 * with higher values displaying in front of markers with lower values.
 *
 * @param onAnimationChanged A callback that will be invoked when the marker animation changes.
 * @param onClick A callback that will be invoked when the marker is clicked.
 * @param onClickableChanged A callback that will be invoked when the marker clickable property changes.
 * @param onContextMenu A callback that will be invoked when the DOM contextmenu event is fired on the marker.
 * @param onCursorChanged A callback that will be invoked when the marker cursor changes.
 * @param onDoubleClick A callback that will be invoked when the marker is double-clicked.
 * @param onDrag A callback that will be invoked repeatedly while the user drags the marker.
 * @param onDragEnd A callback that will be invoked when the user stops dragging the marker.
 * @param onDragStart A callback that will be invoked when the user starts dragging the marker.
 * @param onFlatChanged A callback that will be invoked when the marker flat property changes.
 * @param onIconChanged A callback that will be invoked when the marker icon property changes.
 * @param onMousedown A callback that will be invoked when the mousedown event is fired on the marker.
 * @param onMouseout A callback that will be invoked when the mouse leaves the area of the marker icon.
 * @param onMouseover A callback that will be invoked when the mouse enters the area of the marker icon.
 * @param onMouseup A callback that will be invoked when the mouseup event is fired on the marker.
 * @param onPositionChanged A callback that will be invoked when the marker position property changes.
 * @param onShapeChanged A callback that will be invoked when the marker shape property changes.
 * @param onTitleChanged A callback that will be invoked when the marker title property changes.
 * @param onVisibleChanged A callback that will be invoked when the marker visible property changes.
 * @param onZIndexChanged A callback that will be invoked when the marker zIndex property changes.
 *
 * @param infoContent The content to be displayed in the info window when the marker is clicked.
 */
@Composable
fun Marker(
    state: MarkerState = rememberMarkerState(),
    anchor: Point? = null,
    animation: MarkerAnimation? = null,
    clickable: Boolean = true,
    crossOnDrag: Boolean = true,
    cursor: String? = null,
    draggable: Boolean = false,
    icon: MarkerIcon? = null,
    label: MarkerLabel? = null,
    opacity: Double = 1.0,
    optimized: Boolean? = null,
    shape: MarkerShape? = null,
    title: String? = null,
    visible: Boolean = true,
    zIndex: Double? = null,
    onAnimationChanged: () -> Unit = {},
    onClick: (MapMouseEvent) -> Unit = {},
    onClickableChanged: () -> Unit = {},
    onContextMenu: (MapMouseEvent) -> Unit = {},
    onCursorChanged: () -> Unit = {},
    onDoubleClick: (MapMouseEvent) -> Unit = {},
    onDrag: (MapMouseEvent) -> Unit = {},
    onDragEnd: (MapMouseEvent) -> Unit = {},
    onDraggableChanged: () -> Unit = {},
    onDragStart: (MapMouseEvent) -> Unit = {},
    onFlatChanged: () -> Unit = {},
    onIconChanged: () -> Unit = {},
    onMousedown: (MapMouseEvent) -> Unit = {},
    onMouseout: (MapMouseEvent) -> Unit = {},
    onMouseover: (MapMouseEvent) -> Unit = {},
    onMouseup: (MapMouseEvent) -> Unit = {},
    onPositionChanged: () -> Unit = {},
    onShapeChanged: () -> Unit = {},
    onTitleChanged: () -> Unit = {},
    onVisibleChanged: () -> Unit = {},
    onZIndexChanged: () -> Unit = {},
    infoContent: (@Composable () -> Unit)? = null,
) {
    val mapApplier = currentComposer.applier as MapApplier?
    ComposeNode<MarkerNode, MapApplier>(
        factory = {
            val marker = newMarker(
                jso {
                    this.position = state.position.toJsLatLngLiteral()
                    this.anchorPoint = anchor?.toJsPoint()
                    this.animation = animation?.toJsAnimation()
                    this.clickable = clickable
                    this.crossOnDrag = crossOnDrag
                    this.cursor = cursor
                    this.draggable = draggable
                    this.icon = icon?.toJsMarkerIcon()
                    this.label = label?.toJsMarkerLabel()
                    this.map = mapApplier?.map
                    this.opacity = opacity
                    this.optimized = optimized
                    this.shape = shape?.toJsMarkerShape()
                    this.title = title
                    this.visible = visible
                    this.zIndex = zIndex
                }
            )
            MarkerNode(
                marker = marker,
                markerState = state,
                events = mutableMapOf(),
                infoContent = infoContent,
            )
        },
        update = {
            set(state.position) { marker.setOptions(jso { this.position = state.position.toJsLatLngLiteral() }) }
            set(anchor) {marker.setOptions(jso { this.anchorPoint = it?.toJsPoint() }) }
            set(animation) { marker.setOptions(jso { this.animation = animation?.toJsAnimation() }) }
            set(clickable) { marker.setOptions(jso { this.clickable = clickable }) }
            set(crossOnDrag) { marker.setOptions(jso { this.crossOnDrag = crossOnDrag }) }
            set(cursor) { marker.setOptions(jso { this.cursor = cursor }) }
            set(draggable) { marker.setOptions(jso { this.draggable = draggable }) }
            set(icon) { marker.setOptions(jso { this.icon = icon?.toJsMarkerIcon() }) }
            set(label) { marker.setOptions(jso { this.label = label?.toJsMarkerLabel() }) }
            set(opacity) { marker.setOptions(jso { this.opacity = opacity }) }
            set(optimized) { marker.setOptions(jso { this.optimized = optimized }) }
            set(shape) { marker.setOptions(jso { this.shape = shape?.toJsMarkerShape() }) }
            set(title) { marker.setOptions(jso { this.title = title }) }
            set(visible) { marker.setOptions(jso { this.visible = visible }) }
            set(zIndex) { marker.setOptions(jso { this.zIndex = zIndex }) }

            set(onAnimationChanged) {
                val eventName = "animation_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onAnimationChanged() }
            }
            set(onClick) {
                val eventName = "click"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onClick((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onClickableChanged) {
                val eventName = "clickable_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onClickableChanged() }
            }
            set(onContextMenu) {
                val eventName = "contextmenu"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onContextMenu((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onCursorChanged) {
                val eventName = "cursor_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onCursorChanged() }
            }
            set(onDoubleClick) {
                val eventName = "dblclick"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onDoubleClick((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDrag) {
                val eventName = "drag"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onDrag((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDragEnd) {
                val eventName = "dragend"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onDragEnd((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onDraggableChanged) {
                val eventName = "draggable_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onDraggableChanged() }
            }
            set(onDragStart) {
                val eventName = "dragstart"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onDragStart((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onFlatChanged) {
                val eventName = "flat_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onFlatChanged() }
            }
            set(onIconChanged) {
                val eventName = "icon_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onIconChanged() }
            }
            set(onMousedown) {
                val eventName = "mousedown"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onMousedown((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseout) {
                val eventName = "mouseout"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onMouseout((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseover) {
                val eventName = "mouseover"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onMouseover((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onMouseup) {
                val eventName = "mouseup"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onMouseup((it as JsMapMouseEvent).toMouseEvent()) }
            }
            set(onPositionChanged) {
                val eventName = "position_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onPositionChanged() }
            }
            set(onShapeChanged) {
                val eventName = "shape_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onShapeChanged() }
            }
            set(onTitleChanged) {
                val eventName = "title_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onTitleChanged() }
            }
            set(onVisibleChanged) {
                val eventName = "visible_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onVisibleChanged() }
            }
            set(onZIndexChanged) {
                val eventName = "zindex_changed"
                this.events[eventName]?.remove()
                this.events[eventName] = marker.addListener(eventName) { onZIndexChanged() }
            }
        }
    )
}
