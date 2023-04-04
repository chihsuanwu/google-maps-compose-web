package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.newMap
import com.chihsuanwu.maps.compose.web.jsobject.toJsMapOptions
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.awaitCancellation
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.AttrBuilderContext
import org.jetbrains.compose.web.dom.Div
import org.w3c.dom.HTMLDivElement

/**
 * A compose container for a [MapView].
 *
 * @param apiKey the API key to use for the map.
 * @param cameraPositionState the [CameraPositionState] to be used to control or observe the map's
 * camera state
 * @param mapOptions the [MapOptions] to be used to configure the map.
 * @param id The id of the element to be used as the map container.
 * @param extra The extra parameters to be appended to the Google Maps API URL. For example, you can
 * add `"libraries=geometry"` to load the geometry library.
 * @param attrs The attributes to be applied to the map container.
 *
 * @param onBoundsChanged Called when the viewport bounds have changed.
 * @param onCenterChanged Called when the map center property changes.
 * @param onClick The click listener to be applied to the map.
 * A [MapMouseEvent] will be passed to the listener unless a place icon was clicked, in which case an
 * [IconMouseEvent] will be passed to the listener.
 * @param onContextMenu Called when the DOM contextmenu event is fired on the map container.
 * @param onDoubleClick Called when the DOM dblclick event is fired on the map container.
 * @param onDrag Called repeatedly while the user drags the map.
 * @param onDragEnd Called when the user stops dragging the map.
 * @param onDragStart Called when the user starts dragging the map.
 * @param onHeadingChanged Called when the map heading property changes.
 * @param onIdle Called when the map becomes idle after panning or zooming.
 * @param onIsFractionalZoomEnabledChanged Called when the map isFractionalZoomEnabled property changes.
 * @param onMapTypeIdChanged Called when the map mapTypeId property changes.
 * @param onMouseMove Called when the user's mouse moves over the map container.
 * @param onMouseOut Called when the user's mouse exits the map container.
 * @param onMouseOver Called when the user's mouse enters the map container.
 * @param onProjectionChanged Called when the projection has changed.
 * @param onRenderingTypeChanged Called when the renderingType property changes.
 * @param onTilesLoaded Called when the visible tiles have finished loading.
 * @param onTiltChanged Called when the map tilt property changes.
 * @param onZoomChanged Called when the map zoom property changes.
 *
 * @param content the content of the map
 */
@Composable
fun GoogleMap(
    apiKey: String?,
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    mapOptions: MapOptions = MapOptions(),
    id: String = "map",
    extra: String? = null,
    attrs: AttrBuilderContext<HTMLDivElement>? = null,
    onBoundsChanged: () -> Unit = {},
    onCenterChanged: () -> Unit = {},
    onClick: (MapMouseEvent) -> Unit = {},
    onContextMenu: (MapMouseEvent) -> Unit = {},
    onDoubleClick: (MapMouseEvent) -> Unit = {},
    onDrag: () -> Unit = {},
    onDragEnd: () -> Unit = {},
    onDragStart: () -> Unit = {},
    onHeadingChanged: () -> Unit = {},
    onIdle: () -> Unit = {},
    onIsFractionalZoomEnabledChanged: () -> Unit = {},
    onMapTypeIdChanged: () -> Unit = {},
    onMouseMove: (MapMouseEvent) -> Unit = {},
    onMouseOut: (MapMouseEvent) -> Unit = {},
    onMouseOver: (MapMouseEvent) -> Unit = {},
    onProjectionChanged: () -> Unit = {},
    onRenderingTypeChanged: () -> Unit = {},
    onTilesLoaded: () -> Unit = {},
    onTiltChanged: () -> Unit = {},
    onZoomChanged: () -> Unit = {},
    content: @Composable (() -> Unit)? = null,
) {
    var map: MapView? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val script = document.createElement("script").apply {
            val src = StringBuilder("https://maps.googleapis.com/maps/api/js?")
            apiKey?.let { src.append("key=$it") }
            src.append("&callback=initMap")
            extra?.let { src.append("&$it") }
            this.asDynamic().src = src
            this.asDynamic().async = true
        }
        document.head?.appendChild(script)
    }

    window.asDynamic().initMap = {
        map = newMap(id = id, options = mapOptions.toJsMapOptions())
    }

    val currentCameraPositionState by rememberUpdatedState(cameraPositionState)

    val parentComposition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)

    LaunchedEffect(map) {
        val currentMap = map
        if (currentMap != null) {
            disposingComposition {
                currentMap.newComposition(parentComposition) {
                    MapUpdater(
                        cameraPositionState = currentCameraPositionState,
                        mapOptions = mapOptions,
                        onBoundsChanged = onBoundsChanged,
                        onCenterChanged = onCenterChanged,
                        onClick = onClick,
                        onContextMenu = onContextMenu,
                        onDoubleClick = onDoubleClick,
                        onDrag = onDrag,
                        onDragEnd = onDragEnd,
                        onDragStart = onDragStart,
                        onHeadingChanged = onHeadingChanged,
                        onIdle = onIdle,
                        onIsFractionalZoomEnabledChanged = onIsFractionalZoomEnabledChanged,
                        onMapTypeIdChanged = onMapTypeIdChanged,
                        onMouseMove = onMouseMove,
                        onMouseOut = onMouseOut,
                        onMouseOver = onMouseOver,
                        onProjectionChanged = onProjectionChanged,
                        onRenderingTypeChanged = onRenderingTypeChanged,
                        onTilesLoaded = onTilesLoaded,
                        onTiltChanged = onTiltChanged,
                        onZoomChanged = onZoomChanged,
                    )
                    currentContent?.invoke()
                }
            }
        }
    }

    // The container for the map
    Div(
        attrs = {
            id(id)
            style {
                width(100.percent)
                height(100.percent)
            }
            attrs?.invoke(this)
        }
    )
}

internal suspend inline fun disposingComposition(factory: () -> Composition) {
    val composition = factory()
    try {
        awaitCancellation()
    } finally {
        composition.dispose()
    }
}

private inline fun MapView.newComposition(
    parent: CompositionContext,
    noinline content: @Composable () -> Unit
): Composition {
    return Composition(
        MapApplier(this), parent
    ).apply {
        setContent(content)
    }
}
