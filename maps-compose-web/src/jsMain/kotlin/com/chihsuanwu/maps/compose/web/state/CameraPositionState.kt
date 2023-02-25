package com.chihsuanwu.maps.compose.web.state

import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.element.GoogleMap
import com.chihsuanwu.maps.compose.web.element.toCameraOptions
import kotlinx.browser.document


data class LatLng(
    val lat: Double,
    val lng: Double,
)

data class CameraPosition(
    val center: LatLng,
    val zoom: Double,
    val tilt: Double = 0.0,
    val heading: Double = 0.0,
)

/**
 * Create and [remember] a [CameraPositionState].
 */
@Composable
public inline fun rememberCameraPositionState(
    crossinline init: CameraPositionState.() -> Unit = {},
): CameraPositionState = remember {
    CameraPositionState().apply(init)
}

public class CameraPositionState(
    position: CameraPosition = CameraPosition(
        center = LatLng(0.0, 0.0),
        zoom = 0.0,
    )
) {
    /**
     * Whether the camera is currently moving or not.
     */
    public var isMoving: Boolean by mutableStateOf(false)
        internal set


    internal var rawPosition by mutableStateOf(position)

    /**
     * Current position of the camera on the map.
     */
    public var position: CameraPosition
        get() = rawPosition
        set(value) {
            val map = map
            if (map == null) {
                rawPosition = value
            } else {
                console.log("moveCamera to ${value.center.lat}, ${value.center.lng}}")
                map.moveCamera(value.toCameraOptions())
            }
        }

    private var map: GoogleMap? by mutableStateOf(null)

    internal fun setMap(map: GoogleMap?) {
        if (this.map == null && map == null) return
        if (this.map != null && map != null) {
            error("CameraPositionState may only be associated with one GoogleMap at a time")
        }
        this.map = map
        if (map == null) {
            isMoving = false
        } else {
            map.moveCamera(position.toCameraOptions())
        }
    }
}