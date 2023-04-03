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

class Point(
    val x: Double,
    val y: Double
)

class Size(
    val height: Double,
    val width: Double
)
