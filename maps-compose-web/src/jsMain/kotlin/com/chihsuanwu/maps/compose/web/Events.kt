package com.chihsuanwu.maps.compose.web


open class MapMouseEvent(
    val latLng: LatLng
)

class IconMouseEvent(
    latLng: LatLng,
    val placeId: String
) : MapMouseEvent(latLng)

class PolyMouseEvent(
    latLng: LatLng,
    val edge: Int,
    val path: Int,
    val vertex: Int
) : MapMouseEvent(latLng)
