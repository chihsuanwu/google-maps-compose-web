package com.chihsuanwu.maps.compose.web.jsobject.utils

import com.chihsuanwu.maps.compose.web.Point
import js.core.jso

internal external interface PointJson {
    var x: Double
    var y: Double
}

internal fun Point.toPointJson(): PointJson {
    return jso {
        x = this@toPointJson.x
        y = this@toPointJson.y
    }
}