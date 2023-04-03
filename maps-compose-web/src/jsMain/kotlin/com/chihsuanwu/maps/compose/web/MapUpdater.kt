package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.toLatLng


internal class MapPropertiesNode(
    val map: MapView,
    cameraPositionState: CameraPositionState,
    var events: List<MapsEventListener>,
    var onClick: MapsEventListener?,
) : MapNode {

    init {
        cameraPositionState.setMap(map)
    }

    var cameraPositionState = cameraPositionState
        set(value) {
            if (value == field) return
            field.setMap(null)
            field = value
            value.setMap(map)
        }

    override fun onAttached() {
        map.addListener("idle") {
            cameraPositionState.isMoving = false
            cameraPositionState.rawPosition = CameraPosition(
                map.getCenter().toLatLng(),
                map.getZoom(),
            )
        }
    }

    override fun onRemoved() {
        cameraPositionState.setMap(null)
    }

    override fun onCleared() {
        cameraPositionState.setMap(null)
    }
}

@Composable
internal fun MapUpdater(
    cameraPositionState: CameraPositionState,
    mapOptions: MapOptions,
    events: MapEventsBuilder.() -> Unit,
    onClick: (MouseEvent) -> Unit,
) {
    val map = (currentComposer.applier as MapApplier).map

    ComposeNode<MapPropertiesNode, MapApplier>(
        factory = {
            MapPropertiesNode(
                map = map,
                cameraPositionState = cameraPositionState,
                events = emptyList(),
                onClick = null,
            )
        }
    ) {
        update(cameraPositionState) { this.cameraPositionState = it }
        update(mapOptions) { map.setOptions(it.toJsMapOptions()) }

        set(events) {
            this.events.forEach { it.remove() }
            this.events = MapEventsBuilder().apply(events).build().map { e ->
                when (e) {
                    is Event.Unit -> map.addListener(e.event) { e.callback(it) }
                    is Event.Mouse -> map.addListener(e.event) { e.callback((it as MapMouseEvent).toMouseEvent()) }
                }
            }
        }
        set(onClick) {
            this.onClick?.remove()
            this.onClick = map.addListener("click") { onClick((it as MapMouseEvent).toMouseEvent()) }
        }
    }
}
