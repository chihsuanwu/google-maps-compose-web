import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.*
import js.core.jso
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import kotlin.random.Random

fun main() {
    renderComposable(rootElementId = "root") {
        MainPage()
    }
}

@Composable
private fun MainPage() {
    var apiKey: String by remember { mutableStateOf("") }

    var setApiKeyClicked by remember { mutableStateOf(false) }

    Div({
        style {
            textAlign("center")
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            height(100.vh - 66.px)
            padding(25.px)
        }
    }) {
        Span(
            attrs = {
                style {
                    color(Color("#0000ff"))
                    fontSize(24.px)
                }
            }
        ) {
            Text("Hello, Google Map Compose Web!!")
        }

        Div(
            attrs = {
                style {
                    margin(10.px)
                }
            }
        ) {
            Input(
                type = InputType.Text,
                attrs = {
                    attr("placeholder", "Enter your API key here")
                    value(apiKey)
                    onInput { event ->
                        apiKey = event.value
                    }
                    style {
                        display(DisplayStyle.Inline)
                        padding(4.px)
                    }
                }
            )

            Button(
                attrs = {
                    style {
                        margin(10.px)
                    }
                    onClick {
                        setApiKeyClicked = true
                    }
                }
            ) {
                Text("Set API Key")
            }
        }

        if (setApiKeyClicked) {
            Map(apiKey = apiKey)
        }
    }
}

@Composable
private fun Map(
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

    Div(
        attrs = {
            style {
                margin(5.px)
            }
        }
    ) {
        Span(
            attrs = {
                style {
                    marginRight(10.px)
                }
            }
        ) {
            Text("Camera Position: ${cameraPositionState.position.center.asString()}")
        }
        Button(
            attrs = {
                onClick {
                    val currentCenter = cameraPositionState.position.center
                    val randomRange = 0.5
                    cameraPositionState.position = cameraPositionState.position.copy(
                        center = LatLng(
                            currentCenter.lat + Random.nextDouble(-randomRange, randomRange),
                            currentCenter.lng + Random.nextDouble(-randomRange, randomRange),
                        )
                    )
                }
            }
        ) {
            Text("Move Camera Randomly")
        }
    }

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
    }

    val mapOptions = remember {
        MapOptions(
            fullscreenControlOptions = FullscreenControlOptions(
                position = ControlPosition.RightCenter
            ),
            mapTypeControl = true,
            mapTypeControlOptions = MapTypeControlOptions(
                mapTypeIds = arrayOf(
                    MapTypeId.Roadmap,
                    MapTypeId.Satellite,
                    MapTypeId.Hybrid,
                    MapTypeId.Terrain,
                ),
                position = ControlPosition.TopCenter,
                style = MapTypeControlStyle.DropdownMenu,
            ),
            styles = mapStyle
        )
    }

    GoogleMap(
        apiKey = apiKey,
        cameraPositionState = cameraPositionState,
        mapOptions = mapOptions,
        extra = "libraries=geometry", // Required for decoding path from encoded string
        attrs = {
            style {
                width(100.percent)
                flex(1) // Fill the remaining height
                property("margin", "0 auto") // Center the map
            }
        },
        events = {
            onContextMenu = {
                console.log("Map context menu!, ${it.latLng.asString()}")
            }
            onDrag = {
                console.log("Map dragged!")
            }
            onIdle = {
                console.log("Map idle!")
            }
        },
        onClick = {
            console.log("Map clicked!, ${it.latLng.asString()}")
        }
    ) {
        if (polyline.isNotEmpty()) {
            Polyline(
                points = polyline,
                clickable = true,
                color = "#EE4411",
                opacity = 0.8,
                events = {
                    onDoubleClick = {
                        console.log("Polyline double clicked!, ${it.latLng.asString()}")
                    }
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
                title = "Hello, Marker ${marker.position.asString()}!",
                icon = "https://developers.google.com/maps/documentation/javascript/examples/full/images/beachflag.png",
                draggable = true,
                events = {
                    onDragEnd = {
                        console.log("Marker dragged! New position: ${it.latLng.asString()}")
                    }
                    onDoubleClick = {
                        console.log("Marker double clicked!")
                    }
                },
                onClick = {
                    console.log("Marker clicked at ${it.latLng.asString()}!")
                    marker.showInfoWindow()
                }
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
    }
}

val mapStyle = MapTypeStyles(
    styles = arrayOf(
        MapTypeStyle(
            elementType = "geometry",
            stylers = arrayOf(
                jso {
                    color = "#242f3e"
                }
            )
        ),
        MapTypeStyle(
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#746855"
                }
            )
        ),
        MapTypeStyle(
            elementType = "labels.text.stroke",
            stylers = arrayOf(
                jso {
                    color = "#242f3e"
                }
            )
        ),
        MapTypeStyle(
            featureType = "administrative.locality",
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#d59563"
                }
            )
        ),
        MapTypeStyle(
            featureType = "poi",
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#d59563"
                }
            )
        ),
        MapTypeStyle(
            featureType = "poi.park",
            elementType = "geometry",
            stylers = arrayOf(
                jso {
                    color = "#263c3f"
                }
            )
        ),
        MapTypeStyle(
            featureType = "poi.park",
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#6b9a76"
                }
            )
        ),
        MapTypeStyle(
            featureType = "road",
            elementType = "geometry",
            stylers = arrayOf(
                jso {
                    color = "#38414e"
                }
            )
        ),
        MapTypeStyle(
            featureType = "road",
            elementType = "geometry.stroke",
            stylers = arrayOf(
                jso {
                    color = "#212a37"
                }
            )
        ),
        MapTypeStyle(
            featureType = "road",
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#9ca5b3"
                }
            )
        ),
        MapTypeStyle(
            featureType = "road.highway",
            elementType = "geometry",
            stylers = arrayOf(
                jso {
                    color = "#746855"
                }
            )
        ),
        MapTypeStyle(
            featureType = "road.highway",
            elementType = "geometry.stroke",
            stylers = arrayOf(
                jso {
                    color = "#1f2835"
                }
            )
        ),
        MapTypeStyle(
            featureType = "road.highway",
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#f3d19c"
                }
            )
        ),
        MapTypeStyle(
            featureType = "transit",
            elementType = "geometry",
            stylers = arrayOf(
                jso {
                    color = "#2f3948"
                }
            )
        ),
        MapTypeStyle(
            featureType = "transit.station",
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#d59563"
                }
            )
        ),
        MapTypeStyle(
            featureType = "water",
            elementType = "geometry",
            stylers = arrayOf(
                jso {
                    color = "#17263c"
                }
            )
        ),
        MapTypeStyle(
            featureType = "water",
            elementType = "labels.text.fill",
            stylers = arrayOf(
                jso {
                    color = "#515c6d"
                }
            )
        ),
        MapTypeStyle(
            featureType = "water",
            elementType = "labels.text.stroke",
            stylers = arrayOf(
                jso {
                    color = "#17263c"
                }
            )
        )
    )
)

const val mapStyleString = """
  [{
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#242f3e"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#746855"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#242f3e"
      }
    ]
  },
  {
    "featureType": "administrative.locality",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#d59563"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#d59563"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#263c3f"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#6b9a76"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#38414e"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#212a37"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9ca5b3"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#746855"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#1f2835"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#f3d19c"
      }
    ]
  },
  {
    "featureType": "transit",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#2f3948"
      }
    ]
  },
  {
    "featureType": "transit.station",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#d59563"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#17263c"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#515c6d"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#17263c"
      }
    ]
  }]
"""