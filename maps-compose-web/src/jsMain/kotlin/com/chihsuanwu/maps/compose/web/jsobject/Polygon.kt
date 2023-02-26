package com.chihsuanwu.maps.compose.web.jsobject

/**
 * A [google.maps.Polygon](https://developers.google.com/maps/documentation/javascript/reference/polygon#Polygon.constructor) object
 */
internal external interface JsPolygon {

    fun setMap(map: MapView?)

    fun setOptions(options: PolygonOptions)

}

internal fun newPolygon(options: PolygonOptions): JsPolygon {
    return js("new google.maps.Polygon(options);") as JsPolygon
}

internal external interface PolygonOptions {

    var path: Array<LatLngJson>

    var fillColor: String

    var strokeColor: String

    var strokeWeight: Int

}