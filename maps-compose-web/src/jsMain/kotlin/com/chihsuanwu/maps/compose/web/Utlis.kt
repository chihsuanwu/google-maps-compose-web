package com.chihsuanwu.maps.compose.web


class LatLng(
    val lat: Double,
    val lng: Double,
)

class LatLngBounds(
    val east: Double,
    val north: Double,
    val south: Double,
    val west: Double,
)

enum class StrokePosition {
    CENTER,
    INSIDE,
    OUTSIDE
}

class Point(
    val x: Double,
    val y: Double
)