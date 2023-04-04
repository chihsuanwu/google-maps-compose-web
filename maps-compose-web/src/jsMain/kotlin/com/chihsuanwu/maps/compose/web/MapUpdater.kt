package com.chihsuanwu.maps.compose.web

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.currentComposer
import com.chihsuanwu.maps.compose.web.jsobject.*
import com.chihsuanwu.maps.compose.web.jsobject.toLatLng


internal class MapPropertiesNode(
    val map: MapView,
    cameraPositionState: CameraPositionState,
    var events: MutableMap<String, MapsEventListener>,
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
    onBoundsChanged: () -> Unit,
    onCenterChanged: () -> Unit,
    onClick: (MapMouseEvent) -> Unit,
    onContextMenu: (MapMouseEvent) -> Unit,
    onDoubleClick: (MapMouseEvent) -> Unit,
    onDrag: () -> Unit,
    onDragEnd: () -> Unit,
    onDragStart: () -> Unit,
    onHeadingChanged: () -> Unit,
    onIdle: () -> Unit,
    onIsFractionalZoomEnabledChanged: () -> Unit,
    onMapTypeIdChanged: () -> Unit,
    onMouseMove: (MapMouseEvent) -> Unit,
    onMouseOut: (MapMouseEvent) -> Unit,
    onMouseOver: (MapMouseEvent) -> Unit,
    onProjectionChanged: () -> Unit,
    onRenderingTypeChanged: () -> Unit,
    onTilesLoaded: () -> Unit,
    onTiltChanged: () -> Unit,
    onZoomChanged: () -> Unit,
) {
    val map = (currentComposer.applier as MapApplier).map

    ComposeNode<MapPropertiesNode, MapApplier>(
        factory = {
            MapPropertiesNode(
                map = map,
                cameraPositionState = cameraPositionState,
                events = mutableMapOf(),
            )
        }
    ) {
        update(cameraPositionState) { this.cameraPositionState = it }
        update(mapOptions) { map.setOptions(it.toJsMapOptions()) }

        set(onBoundsChanged) {
            val eventName = "bounds_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onBoundsChanged() }
        }
        set(onCenterChanged) {
            val eventName = "center_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onCenterChanged() }
        }
        set(onClick) {
            val eventName = "click"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onClick((it as JsMapMouseEvent).toMouseEvent()) }
        }
        set(onContextMenu) {
            val eventName = "contextmenu"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onContextMenu((it as JsMapMouseEvent).toMouseEvent()) }
        }
        set(onDoubleClick) {
            val eventName = "dblclick"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onDoubleClick((it as JsMapMouseEvent).toMouseEvent()) }
        }
        set(onDrag) {
            val eventName = "drag"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onDrag() }
        }
        set(onDragEnd) {
            val eventName = "dragend"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onDragEnd() }
        }
        set(onDragStart) {
            val eventName = "dragstart"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onDragStart() }
        }
        set(onHeadingChanged) {
            val eventName = "heading_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onHeadingChanged() }
        }
        set(onIdle) {
            val eventName = "idle"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onIdle() }
        }
        set(onIsFractionalZoomEnabledChanged) {
            val eventName = "isfractionalzoomenabled_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onIsFractionalZoomEnabledChanged() }
        }
        set(onMapTypeIdChanged) {
            val eventName = "maptypeid_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onMapTypeIdChanged() }
        }
        set(onMouseMove) {
            val eventName = "mousemove"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onMouseMove((it as JsMapMouseEvent).toMouseEvent()) }
        }
        set(onMouseOut) {
            val eventName = "mouseout"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onMouseOut((it as JsMapMouseEvent).toMouseEvent()) }
        }
        set(onMouseOver) {
            val eventName = "mouseover"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onMouseOver((it as JsMapMouseEvent).toMouseEvent()) }
        }
        set(onProjectionChanged) {
            val eventName = "projection_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onProjectionChanged() }
        }
        set(onRenderingTypeChanged) {
            val eventName = "renderingtype_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onRenderingTypeChanged() }
        }
        set(onTilesLoaded) {
            val eventName = "tilesloaded"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onTilesLoaded() }
        }
        set(onTiltChanged) {
            val eventName = "tilt_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onTiltChanged() }
        }
        set(onZoomChanged) {
            val eventName = "zoom_changed"
            this.events[eventName]?.remove()
            this.events[eventName] = map.addListener(eventName) { onZoomChanged() }
        }
    }
}
