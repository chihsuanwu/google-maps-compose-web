package com.chihsuanwu.maps.compose.web

import com.chihsuanwu.maps.compose.web.layers.KMLFeatureData


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

class KMLMouseEvent(
    latLng: LatLng,
    val featureData: KMLFeatureData,
    val pixelOffset: Size,
) : MapMouseEvent(latLng)
