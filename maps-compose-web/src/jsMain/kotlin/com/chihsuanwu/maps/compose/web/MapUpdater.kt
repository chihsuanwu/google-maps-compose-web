package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.MapView
import com.chihsuanwu.maps.compose.web.jsobject.utils.toLatLng


internal class MapPropertiesNode(
    val map: MapView,
    cameraPositionState: CameraPositionState,
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
    cameraPositionState: CameraPositionState
) {
    val map = (currentComposer.applier as MapApplier).map

    ComposeNode<MapPropertiesNode, MapApplier>(
        factory = {
            MapPropertiesNode(
                map = map,
                cameraPositionState = cameraPositionState,
            )
        }
    ) {
        update(cameraPositionState) { this.cameraPositionState = it }
    }
}
