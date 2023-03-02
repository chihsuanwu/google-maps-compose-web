import com.chihsuanwu.maps.compose.web.LatLng

fun LatLng.asString(): String {
    return "(${this.lat.format(5)}, ${this.lng.format(5)})"
}

fun String.decodePath(): List<LatLng> {
    val encodedPath = this
    val result = js("google.maps.geometry.encoding.decodePath(encodedPath)") as Array<dynamic>
    return result.map { LatLng(it.lat() as Double, it.lng() as Double) }
}

private fun Double.format(digits: Int) = this.asDynamic().toFixed(digits) as String
