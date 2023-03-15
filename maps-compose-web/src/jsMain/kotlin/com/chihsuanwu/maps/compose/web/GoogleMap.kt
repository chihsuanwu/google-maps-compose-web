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
