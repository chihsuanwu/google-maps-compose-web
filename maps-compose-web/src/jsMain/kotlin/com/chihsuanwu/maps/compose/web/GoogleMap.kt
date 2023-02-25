package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.element.GoogleMap
import com.chihsuanwu.maps.compose.web.element.googleMap
import com.chihsuanwu.maps.compose.web.element.toLatLngJson
import com.chihsuanwu.maps.compose.web.state.CameraPositionState
import com.chihsuanwu.maps.compose.web.state.rememberCameraPositionState
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.awaitCancellation
import org.jetbrains.compose.web.dom.*
import org.w3c.dom.HTMLDivElement

@Composable
public fun GoogleMap(
    cameraPositionState: CameraPositionState = rememberCameraPositionState(),
    id: String = "map",
    attrs: AttrBuilderContext<HTMLDivElement>? = null,
) {
    var map: GoogleMap? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val script = document.createElement("script").apply {
            this.asDynamic().src = "https://maps.googleapis.com/maps/api/js?callback=initMap"
            this.asDynamic().async = true
        }
        document.head?.appendChild(script)
    }

    window.asDynamic().initMap = {
        map = googleMap(
            id = id,
            center = cameraPositionState.position.center.toLatLngJson(),
            zoom = cameraPositionState.position.zoom
        )
    }

    val currentCameraPositionState by rememberUpdatedState(cameraPositionState)

    val parentComposition = rememberCompositionContext()

    LaunchedEffect(map) {
        val currentMap = map
        if (currentMap != null) {
            disposingComposition {
                currentMap.newComposition(parentComposition) {
                    MapUpdater(
                        cameraPositionState = currentCameraPositionState,
                    )
                }
            }
        }
    }

    Div(
        attrs = {
            id("map")
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

private inline fun GoogleMap.newComposition(
    parent: CompositionContext,
    noinline content: @Composable () -> Unit
): Composition {
    return Composition(
        MapApplier(this), parent
    ).apply {
        setContent(content)
    }
}
