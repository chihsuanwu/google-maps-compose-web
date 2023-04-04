import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text

@Composable
fun DrawingExample(
    apiKey: String,
) {
    val cameraPositionState = rememberCameraPositionState  {
        position = CameraPosition(
            center = LatLng(23.5, 120.8),
            zoom = 7.6,
        )
    }

    var polyline: List<LatLng> by remember { mutableStateOf(emptyList()) }
    var polygon: List<LatLng> by remember { mutableStateOf(emptyList()) }
    var markers: List<MarkerState> by remember { mutableStateOf(emptyList()) }
    val infoWindowState: InfoWindowState = remember { InfoWindowState(
        LatLng(23.5, 120.8),
    ) }

    Div(
        attrs = {
            style {
                margin(5.px)
            }
        }
    ) {
        if (polyline.isNotEmpty()) {
            Span(
                attrs = {
                    style {
                        color(Color("#EE4411"))
                        fontWeight("bold")
                    }
                }
            ) {
                Text("Taiwan High Speed Rail")
            }
        }
        Button(
            attrs = {
                style {
                    marginLeft(10.px)
                    marginRight(10.px)
                }
                onClick {
                    if (polyline.isNotEmpty()) {
                        polyline = emptyList()
                        return@onClick
                    }
                    val path = "{d|wCkjfeVv^|rP`uEvgInChuo@l_g@zaa@fpf@l}h@pk_Bb{g@|mm@dcGh}Yzz]jbu@nhQllgBjdFzfm@_fC"
                    polyline = path.decodePath()
                }
            }
        ) {
            Text(if (polyline.isEmpty()) "Draw Polyline" else "Clear Polyline")
        }

        if (polygon.isNotEmpty()) {
            Span(
                attrs = {
                    style {
                        color(Color("#EE4411"))
                        fontWeight("bold")
                        marginLeft(10.px)
                    }
                }
            ) {
                Text("Taipei City")
            }
        }
        Button(
            attrs = {
                style {
                    marginLeft(10.px)
                    marginRight(10.px)
                }
                onClick {
                    if (polygon.isNotEmpty()) {
                        polygon = emptyList()
                        return@onClick
                    }
                    val path = """
                        odwwCwmqeVhWtG{BhbEns@|nDp|ChRf`BcAze@o{Btm@jhCa`@vnCtHp_BoyBre@gZ~eBi{@
                        zi@_dAfBgfA~eBxkAzrBgj@~c@}lC`^yh@wbBevCwh@qcD~_Bap@vaEeqBfCgtDs{CykCyqB
                        hWycB{wCs|CaaBqnBl~AgfBvlCsGd{@wdAdcEk}AjxBtt@h{A_oDbiE`l@|kCaO|eAslC~Oc~B
                    """.trimIndent().replace("\n", "")
                    polygon = path.decodePath()
                }
            }
        ) {
            Text(if (polygon.isEmpty()) "Draw Polygon" else "Clear Polygon")
        }

        Button(
            attrs = {
                style {
                    marginLeft(10.px)
                }
                onClick {
                    markers = markers + listOf(
                        MarkerState(
                            position = LatLng(
                                cameraPositionState.position.center.lat,
                                cameraPositionState.position.center.lng,
                            )
                        )
                    )
                }
            }
        ) {
            Text("Add Marker At Camera Center")
        }

        Button(
            attrs = {
                style {
                    marginLeft(10.px)
                }
                onClick {
                    markers = emptyList()
                }
            }
        ) {
            Text("Clear Markers")
        }

        Button(
            attrs = {
                style {
                    marginLeft(10.px)
                }
                onClick {
                    infoWindowState.showInfoWindow()
                }
            }
        ) {
            Text("Show InfoWindow")
        }
    }

    GoogleMap(
        apiKey = apiKey,
        cameraPositionState = cameraPositionState,
        extra = "libraries=geometry", // Required for decoding path from encoded string
        attrs = {
            style {
                width(100.percent)
                flex(1) // Fill the remaining height
                property("margin", "0 auto") // Center the map
            }
        }
    ) {
        if (polyline.isNotEmpty()) {
            Polyline(
                points = polyline,
                clickable = true,
                color = "#EE4411",
                opacity = 0.8,
                onDoubleClick = {
                    console.log("Polyline double clicked!, ${it.latLng.asString()}")
                }
            ) {
                console.log("Polyline clicked!")
            }
        }

        if (polygon.isNotEmpty()) {
            Polygon(
                points = polygon,
                fillColor = "#EE4411",
                fillOpacity = 0.38,
                strokeColor = "#DD8822",
            )
        }

        markers.forEach { marker ->
            Marker(
                state = marker,
                animation = MarkerAnimation.BOUNCE,
                title = "Hello, Marker ${marker.position.asString()}!",
                icon = MarkerIcon.URL(url = "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png"),
                draggable = true,
                onClick = {
                    console.log("Marker clicked at ${it.latLng.asString()}!")
                    marker.showInfoWindow()
                },
                onDragEnd = {
                    console.log("Marker dragged! New position: ${it.latLng.asString()}")
                },
                onDoubleClick = {
                    console.log("Marker double clicked!")
                },
            ) {
                Div(
                    attrs = {
                        style {
                            backgroundColor(Color("#0066BB"))
                            padding(5.px)
                            borderRadius(5.px)
                        }
                    }
                ) {
                    Span({
                        style {
                            color(Color("#FFFFFF"))
                            fontWeight("bold")
                        }
                    }) {
                        Text("Hello, Marker ${marker.position.asString()}!")
                    }

                    Button({
                        style {
                            color(Color("#FFCC00"))
                            marginLeft(10.px)
                        }
                        onClick {
                            marker.hideInfoWindow()
                        }
                    }) {
                        Text("Close")
                    }
                }
            }
        }

        InfoWindow(
            state = infoWindowState
        ) {
            Div(
                attrs = {
                    style {
                        backgroundColor(Color("#0066BB"))
                        padding(5.px)
                        borderRadius(5.px)
                    }
                }
            ) {
                Span({
                    style {
                        color(Color("#FFFFFF"))
                        fontWeight("bold")
                    }
                }) {
                    Text("Hello, InfoWindow ${infoWindowState.position.asString()}!")
                }

                Button({
                    style {
                        color(Color("#FFCC00"))
                        marginLeft(10.px)
                    }
                    onClick {
                        infoWindowState.hideInfoWindow()
                    }
                }) {
                    Text("Close")
                }
            }
        }
    }
}
