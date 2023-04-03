import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.*
import js.core.jso
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Span
import org.jetbrains.compose.web.dom.Text
import kotlin.random.Random


data class MapListenerState(
    var boundsChanged: Int = 0,
    var centerChanged: Int = 0,
    var click: Int = 0,
    var contextMenu: Int = 0,
    var dblClick: Int = 0,
    var drag: Int = 0,
    var dragEnd: Int = 0,
    var dragStart: Int = 0,
    var headingChanged: Int = 0,
    var idle: Int = 0,
    var mapTypeIdChanged: Int = 0,
    var mouseMove: Int = 0,
    var mouseOut: Int = 0,
    var mouseOver: Int = 0,
    var projectionChanged: Int = 0,
    var renderingTypeChanged: Int = 0,
    var tilesLoaded: Int = 0,
    var tiltChanged: Int = 0,
    var zoomChanged: Int = 0,
)

@Composable
fun MapExample(
    apiKey: String,
) {
    val coroutineScope = rememberCoroutineScope()

    var mapListenerState: MapListenerState by remember { mutableStateOf(MapListenerState()) }

    val cameraPositionState = rememberCameraPositionState  {
        position = CameraPosition(
            center = LatLng(23.5, 120.8),
            zoom = 7.6,
        )
    }

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

    val mapOptions = remember {
        MapOptions(
            fullscreenControlOptions = FullscreenControlOptions(
                position = ControlPosition.TopRight
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

    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
            flex(1)
        }
    }) {
        GoogleMap(
            apiKey = apiKey,
            cameraPositionState = cameraPositionState,
            mapOptions = mapOptions,
            attrs = {
                style {
                    width(90.percent)
                }
            },
            events = {
                onBoundsChanged = {
                    mapListenerState = mapListenerState.copy(boundsChanged = mapListenerState.boundsChanged + 1)
                }
                onCenterChanged = {
                    mapListenerState = mapListenerState.copy(centerChanged = mapListenerState.centerChanged + 1)
                }
                onContextMenu = {
                    mapListenerState = mapListenerState.copy(contextMenu = mapListenerState.contextMenu + 1)
                }
                onDoubleClick = {
                    mapListenerState = mapListenerState.copy(dblClick = mapListenerState.dblClick + 1)
                }
                onDrag = {
                    mapListenerState = mapListenerState.copy(drag = mapListenerState.drag + 1)
                }
                onDragEnd = {
                    mapListenerState = mapListenerState.copy(dragEnd = mapListenerState.dragEnd + 1)
                }
                onDragStart = {
                    mapListenerState = mapListenerState.copy(dragStart = mapListenerState.dragStart + 1)
                }
                onHeadingChanged = {
                    mapListenerState = mapListenerState.copy(headingChanged = mapListenerState.headingChanged + 1)
                }
                onIdle = {
                    mapListenerState = mapListenerState.copy(idle = mapListenerState.idle + 1)
                }
                onMapTypeIdChanged = {
                    mapListenerState = mapListenerState.copy(mapTypeIdChanged = mapListenerState.mapTypeIdChanged + 1)
                }
                onMouseMove = {
                    mapListenerState = mapListenerState.copy(mouseMove = mapListenerState.mouseMove + 1)
                }
                onMouseOut = {
                    mapListenerState = mapListenerState.copy(mouseOut = mapListenerState.mouseOut + 1)
                }
                onMouseOver = {
                    mapListenerState = mapListenerState.copy(mouseOver = mapListenerState.mouseOver + 1)
                }
                onProjectionChanged = {
                    mapListenerState = mapListenerState.copy(projectionChanged = mapListenerState.projectionChanged + 1)
                }
                onRenderingTypeChanged = {
                    mapListenerState = mapListenerState.copy(renderingTypeChanged = mapListenerState.renderingTypeChanged + 1)
                }
                onTilesLoaded = {
                    mapListenerState = mapListenerState.copy(tilesLoaded = mapListenerState.tilesLoaded + 1)
                }
                onTiltChanged = {
                    mapListenerState = mapListenerState.copy(tiltChanged = mapListenerState.tiltChanged + 1)
                }
                onZoomChanged = {
                    mapListenerState = mapListenerState.copy(zoomChanged = mapListenerState.zoomChanged + 1)
                }
            },
            onClick = {
                mapListenerState = mapListenerState.copy(click = mapListenerState.click + 1)
            }
        ) {

        }

        Div({
            style {
                width(10.percent)
                display(DisplayStyle.Flex)
                flexDirection(FlexDirection.Column)
                justifyContent(JustifyContent.SpaceBetween)
            }
        }) {
            ListenerState("Bounds Changed", mapListenerState.boundsChanged)
            ListenerState("Center Changed", mapListenerState.centerChanged)
            ListenerState("Context Menu", mapListenerState.contextMenu)
            ListenerState("Click", mapListenerState.click)
            ListenerState("Double Click", mapListenerState.dblClick)
            ListenerState("Drag", mapListenerState.drag)
            ListenerState("Drag End", mapListenerState.dragEnd)
            ListenerState("Drag Start", mapListenerState.dragStart)
            ListenerState("Heading Changed", mapListenerState.headingChanged)
            ListenerState("Idle", mapListenerState.idle)
            ListenerState("Map Type Id Changed", mapListenerState.mapTypeIdChanged)
            ListenerState("Mouse Move", mapListenerState.mouseMove)
            ListenerState("Mouse Out", mapListenerState.mouseOut)
            ListenerState("Mouse Over", mapListenerState.mouseOver)
            ListenerState("Projection Changed", mapListenerState.projectionChanged)
            ListenerState("Rendering Type Changed", mapListenerState.renderingTypeChanged)
            ListenerState("Tiles Loaded", mapListenerState.tilesLoaded)
            ListenerState("Tilt Changed", mapListenerState.tiltChanged)
            ListenerState("Zoom Changed", mapListenerState.zoomChanged)
        }
    }
}

@Composable
private fun ListenerState(event: String, count: Int) {
    var triggered by remember { mutableStateOf(false) }
    LaunchedEffect(count) {
        triggered = true
        delay(1000)
        triggered = false
    }
    Span({
        style {
            if (triggered) {
                background("#ff0000")
            }
        }
    }) {
        Text(event)
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