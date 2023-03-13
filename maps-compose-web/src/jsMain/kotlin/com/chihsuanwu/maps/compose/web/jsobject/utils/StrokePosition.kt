package com.chihsuanwu.maps.compose.web.jsobject.utils

import com.chihsuanwu.maps.compose.web.StrokePosition

internal fun StrokePosition.toJs(): dynamic {
    return when (this) {
        StrokePosition.CENTER -> js("google.maps.StrokePosition.CENTER")
        StrokePosition.INSIDE -> js("google.maps.StrokePosition.INSIDE")
        StrokePosition.OUTSIDE -> js("google.maps.StrokePosition.OUTSIDE")
    }
}