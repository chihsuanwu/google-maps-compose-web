import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.*
import com.chihsuanwu.maps.compose.web.drawing.*
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import kotlin.random.Random


private class State {
    var cameraPositionState: CameraPositionState = CameraPositionState(
        CameraPosition(
            center = LatLng(23.5, 120.8),
            zoom = 7.6,
        )
    )
    var polyline: List<LatLng> by mutableStateOf(emptyList())
    var polygon: List<LatLng> by mutableStateOf(emptyList())
    var markers: List<MarkerState> by mutableStateOf(emptyList())
    var infoWindowState: InfoWindowState by mutableStateOf(
        InfoWindowState(
            LatLng(23.47, 120.96),
        )
    )
    var overlayViewBounds: LatLngBounds? by mutableStateOf(null)
    var overlayViewLayer: MapPanes by mutableStateOf(MapPanes.OverlayLayer)
}

@Composable
fun DrawingExample(
    apiKey: String,
) {
    val state = remember { State() }

    ToolBar(state)

    GoogleMap(
        apiKey = apiKey,
        cameraPositionState = state.cameraPositionState,
        extra = "libraries=geometry", // Required for decoding path from encoded string
        attrs = {
            style {
                width(100.percent)
                flex(1) // Fill the remaining height
                property("margin", "0 auto") // Center the map
            }
        }
    ) {
        if (state.polyline.isNotEmpty()) {
            Polyline(
                points = state.polyline,
                clickable = true,
                color = "#EE4411",
                icons = listOf(
                    IconSequence(
                        icon = MarkerIcon.Symbol(
                            path = MarkerIcon.Symbol.Path.SymbolPath.Circle,
                            scale = 5.0,
                            strokeColor = "#33FF22",
                        ),
                        repeat = "100%"
                    ),
                ),
                opacity = 0.8,
                onClick = {
                    console.log("Polyline clicked!")
                },
                onDoubleClick = {
                    console.log("Polyline double clicked!, ${it.latLng.asString()}")
                },
            )
        }

        if (state.polygon.isNotEmpty()) {
            Polygon(
                points = state.polygon,
                fillColor = "#EE4411",
                fillOpacity = 0.38,
                strokeColor = "#DD8822",
            )
        }

        state.markers.forEach { marker ->
            Marker(
                state = marker,
                animation = MarkerAnimation.BOUNCE,
                title = "Hello, Marker ${marker.position.asString()}!",
//                icon = MarkerIcon.URL(url = "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png"),
                icon = MarkerIcon.Symbol(
                    path = MarkerIcon.Symbol.Path.SymbolPath.BackwardClosedArrow,
                    scale = 5.0,
                ),
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
            state = state.infoWindowState,
            maxWidth = 200,
        ) {
            Div(
                attrs = {
                    style {
                        backgroundColor(Color("#11CC44"))
                        padding(5.px)
                        borderRadius(5.px)
                        display(DisplayStyle.Flex)
                        flexDirection(FlexDirection.Column)
                        justifyContent(JustifyContent.Center)
                        alignItems(AlignItems.Center)
                    }
                }
            ) {
                Span({
                    style {
                        color(Color("#FFFFFF"))
                        fontWeight("bold")
                    }
                }) {
                    Text("Taiwan's highest mountain")
                }

                Text("Mount Yu-Shan (also known as Jade Mountain) is the highest mountain in Taiwan, at 3,952 m (12,966 ft) above sea level.")
            }
        }

        state.overlayViewBounds?.let {
            OverlayView(
                bounds = it,
                mapPane = state.overlayViewLayer,
            ) {
                Div(
                    attrs = {
                        style {
                            backgroundColor(Color("#FFCC88"))
                            padding(15.px)
                            borderRadius(15.px)
                            display(DisplayStyle.Flex)
                            flexDirection(FlexDirection.Column)
                            justifyContent(JustifyContent.Center)
                            alignItems(AlignItems.Center)
                            width(100.percent)
                            height(100.percent)
                        }
                    }
                ) {
                    var clicked by remember { mutableStateOf(false) }
                    Text("Hello, OverlayView! In ${state.overlayViewLayer}")
                    Button({
                        onClick {
                            clicked = !clicked
                        }
                    }) {
                        Text("Click me!")
                    }
                    if (clicked) {
                        Text("The content can be recomposed dynamically!")
                    }
                }
            }
        }

    }
}

@Composable
private fun ToolBar(state: State) {
    Div {
        Div(
            attrs = {
                style {
                    margin(5.px)
                }
            }
        ) {
            if (state.polyline.isNotEmpty()) {
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
                        if (state.polyline.isNotEmpty()) {
                            state.polyline = emptyList()
                            return@onClick
                        }
                        val path =
                            "{d|wCkjfeVv^|rP`uEvgInChuo@l_g@zaa@fpf@l}h@pk_Bb{g@|mm@dcGh}Yzz]jbu@nhQllgBjdFzfm@_fC"
                        state.polyline = path.decodePath()
                    }
                }
            ) {
                Text(if (state.polyline.isEmpty()) "Draw Polyline" else "Clear Polyline")
            }

            if (state.polygon.isNotEmpty()) {
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
                        if (state.polygon.isNotEmpty()) {
                            state.polygon = emptyList()
                            return@onClick
                        }
                        val path = """
                        odwwCwmqeVhWtG{BhbEns@|nDp|ChRf`BcAze@o{Btm@jhCa`@vnCtHp_BoyBre@gZ~eBi{@
                        zi@_dAfBgfA~eBxkAzrBgj@~c@}lC`^yh@wbBevCwh@qcD~_Bap@vaEeqBfCgtDs{CykCyqB
                        hWycB{wCs|CaaBqnBl~AgfBvlCsGd{@wdAdcEk}AjxBtt@h{A_oDbiE`l@|kCaO|eAslC~Oc~B
                    """.trimIndent().replace("\n", "")
                        state.polygon = path.decodePath()
                    }
                }
            ) {
                Text(if (state.polygon.isEmpty()) "Draw Polygon" else "Clear Polygon")
            }

            Button(
                attrs = {
                    style {
                        marginLeft(10.px)
                    }
                    onClick {
                        state.markers += listOf(
                            MarkerState(
                                position = LatLng(
                                    state.cameraPositionState.position.center.lat,
                                    state.cameraPositionState.position.center.lng,
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
                        state.markers = emptyList()
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
                        state.infoWindowState.showInfoWindow()
                    }
                }
            ) {
                Text("Show InfoWindow")
            }
        }

        Div(
            attrs = {
                style {
                    margin(5.px)
                }
            }
        ) {
            if (state.overlayViewBounds != null) {
                Button(
                    attrs = {
                        style {
                            marginLeft(10.px)
                        }
                        onClick {
                            state.overlayViewBounds = LatLngBounds(
                                east = Random.nextDouble(121.0, 122.0),
                                north = Random.nextDouble(23.0, 24.0),
                                south = Random.nextDouble(22.0, 23.0),
                                west = Random.nextDouble(120.0, 121.0),
                            )
                        }
                    }
                ) {
                    Text("Move OverlayView Randomly")
                }

                Button(
                    attrs = {
                        style {
                            marginLeft(10.px)
                        }
                        onClick {
                            val index = state.overlayViewLayer.ordinal + 1
                            state.overlayViewLayer = MapPanes.values()[index % MapPanes.values().size]
                        }
                    }
                ) {
                    Text("Change OverlayView Layer, Current: ${state.overlayViewLayer}")
                }

                Button(
                    attrs = {
                        style {
                            marginLeft(10.px)
                        }
                        onClick {
                            state.overlayViewBounds = null
                        }
                    }
                ) {
                    Text("Hide OverlayView")
                }
            } else {
                Button(
                    attrs = {
                        style {
                            marginLeft(10.px)
                        }
                        onClick {
                            state.overlayViewBounds = LatLngBounds(
                                east = Random.nextDouble(121.0, 122.0),
                                north = Random.nextDouble(23.0, 24.0),
                                south = Random.nextDouble(22.0, 23.0),
                                west = Random.nextDouble(120.0, 121.0),
                            )
                        }
                    }
                ) {
                    Text("Show OverlayView")
                }
            }
        }
    }
}