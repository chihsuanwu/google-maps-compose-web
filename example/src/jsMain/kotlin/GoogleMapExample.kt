import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.*
import js.core.jso
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.ExperimentalComposeWebApi
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import kotlin.random.Random


private class MapListenerState {
    var boundsChanged: Int by mutableStateOf(0)
    var centerChanged: Int by mutableStateOf(0)
    var click: Int by mutableStateOf(0)
    var contextMenu: Int by mutableStateOf(0)
    var dblClick: Int by mutableStateOf(0)
    var drag: Int by mutableStateOf(0)
    var dragEnd: Int by mutableStateOf(0)
    var dragStart: Int by mutableStateOf(0)
    var headingChanged: Int by mutableStateOf(0)
    var idle: Int by mutableStateOf(0)
    var mapTypeIdChanged: Int by mutableStateOf(0)
    var mouseMove: Int by mutableStateOf(0)
    var mouseOut: Int by mutableStateOf(0)
    var mouseOver: Int by mutableStateOf(0)
    var projectionChanged: Int by mutableStateOf(0)
    var renderingTypeChanged: Int by mutableStateOf(0)
    var tilesLoaded: Int by mutableStateOf(0)
    var tiltChanged: Int by mutableStateOf(0)
    var zoomChanged: Int by mutableStateOf(0)
}

private enum class MapStyle {
    Default,
    Night,
    Retro,
}

@Composable
fun MapExample(
    apiKey: String,
) {
    val mapListenerState: MapListenerState by remember { mutableStateOf(MapListenerState()) }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition(
            center = LatLng(40.71403, 285.99708),
            zoom = 13.0,
        )
    }

    var mapStyle by remember { mutableStateOf(MapStyle.Default) }

    val mapOptions = remember(mapStyle) {
        MapOptions(
            fullscreenControlOptions = FullscreenControlOptions(
                position = ControlPosition.TopRight
            ),
            mapTypeControl = true,
            mapTypeControlOptions = MapTypeControlOptions(
                mapTypeIds = listOf(
                    MapTypeId.Roadmap,
                    MapTypeId.Satellite,
                    MapTypeId.Hybrid,
                    MapTypeId.Terrain,
                ),
                position = ControlPosition.TopCenter,
                style = MapTypeControlStyle.DropdownMenu,
            ),
            styles = when (mapStyle) {
                MapStyle.Default -> null
                MapStyle.Night -> NightStyle
                MapStyle.Retro -> MapTypeStyles.fromString(RetroStyleString)
            }
        )
    }

    CameraRow(cameraPositionState)

//    MapLayerRow(mapLayerState)

    MapStyleRow(mapStyle) {
        mapStyle = it
    }

    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
            flex(1)
        }
    }) {
        MapContent(
            apiKey = apiKey,
            mapOptions = mapOptions,
            cameraPositionState = cameraPositionState,
            listenerState = mapListenerState,
        )

        ListenerColumn(mapListenerState)
    }
}

@Composable
private fun CameraRow(
    state: CameraPositionState
) {
    Div({
        style { margin(5.px) }
    }) {
        Span({
            style { marginRight(10.px) }
        }) {
            Text("Camera Position: ${state.position.center.asString()}")
        }
        Button(
            attrs = {
                onClick {
                    val currentCenter = state.position.center
                    val randomRange = 0.5
                    state.position = state.position.copy(
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
}

@OptIn(ExperimentalComposeWebApi::class)
@Composable
private fun MapStyleRow(
    style: MapStyle,
    onStyleChange: (MapStyle) -> Unit,
) {
    Div({
        style { margin(5.px) }
    }) {
        Span({
            style { marginRight(10.px) }
        }) {
            Text("Map Style: ")
        }
        RadioGroup(checkedValue = style.name) {
            RadioInput(value = "Default") {
                onClick { onStyleChange(MapStyle.Default) }
            }
            Text("Default")
            RadioInput(value = "Night") {
                onClick { onStyleChange(MapStyle.Night) }
            }
            Text("Night")
            RadioInput(value = "Retro") {
                onClick { onStyleChange(MapStyle.Retro) }
            }
            Text("Retro")
        }
    }
}

@Composable
private fun MapContent(
    apiKey: String,
    cameraPositionState: CameraPositionState,
    mapOptions: MapOptions,
    listenerState: MapListenerState,
) {
    GoogleMap(
        apiKey = apiKey,
        cameraPositionState = cameraPositionState,
        mapOptions = mapOptions,
        attrs = {
            style {
                width(90.percent)
            }
        },
        onBoundsChanged = { listenerState.boundsChanged++ },
        onCenterChanged = { listenerState.centerChanged++ },
        onContextMenu = { listenerState.contextMenu++ },
        onClick = { listenerState.click++ },
        onDoubleClick = { listenerState.dblClick++ },
        onDrag = { listenerState.drag++ },
        onDragEnd = { listenerState.dragEnd++ },
        onDragStart = { listenerState.dragStart++ },
        onHeadingChanged = { listenerState.headingChanged++ },
        onIdle = { listenerState.idle++ },
        onMapTypeIdChanged = { listenerState.mapTypeIdChanged++ },
        onMouseMove = { listenerState.mouseMove++ },
        onMouseOut = { listenerState.mouseOut++ },
        onMouseOver = { listenerState.mouseOver++ },
        onProjectionChanged = { listenerState.projectionChanged++ },
        onRenderingTypeChanged = { listenerState.renderingTypeChanged++ },
        onTilesLoaded = { listenerState.tilesLoaded++ },
        onTiltChanged = { listenerState.tiltChanged++ },
        onZoomChanged = { listenerState.zoomChanged++ },
    ) {}
}

@Composable
private fun ListenerColumn(
    state: MapListenerState,
) {
    Div({
        style {
            width(10.percent)
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Column)
            justifyContent(JustifyContent.SpaceBetween)
        }
    }) {
        ListenerState("Bounds Changed", state.boundsChanged)
        ListenerState("Center Changed", state.centerChanged)
        ListenerState("Context Menu", state.contextMenu)
        ListenerState("Click", state.click)
        ListenerState("Double Click", state.dblClick)
        ListenerState("Drag", state.drag)
        ListenerState("Drag End", state.dragEnd)
        ListenerState("Drag Start", state.dragStart)
        ListenerState("Heading Changed", state.headingChanged)
        ListenerState("Idle", state.idle)
        ListenerState("Map Type Id Changed", state.mapTypeIdChanged)
        ListenerState("Mouse Move", state.mouseMove)
        ListenerState("Mouse Out", state.mouseOut)
        ListenerState("Mouse Over", state.mouseOver)
        ListenerState("Projection Changed", state.projectionChanged)
        ListenerState("Rendering Type Changed", state.renderingTypeChanged)
        ListenerState("Tiles Loaded", state.tilesLoaded)
        ListenerState("Tilt Changed", state.tiltChanged)
        ListenerState("Zoom Changed", state.zoomChanged)
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

private val NightStyle = MapTypeStyles(
    listOf(
        MapTypeStyle(
            elementType = "geometry",
            stylers = listOf(jso { color = "#242f3e" })
        ),
        MapTypeStyle(
            elementType = "labels.text.stroke",
            stylers = listOf(jso { color = "#242f3e" })
        ),
        MapTypeStyle(
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#746855" })
        ),
        MapTypeStyle(
            featureType = "administrative.locality",
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#d59563" })
        ),
        MapTypeStyle(
            featureType = "poi",
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#d59563" })
        ),
        MapTypeStyle(
            featureType = "poi.park",
            elementType = "geometry",
            stylers = listOf(jso { color = "#263c3f" })
        ),
        MapTypeStyle(
            featureType = "poi.park",
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#6b9a76" })
        ),
        MapTypeStyle(
            featureType = "road",
            elementType = "geometry",
            stylers = listOf(jso { color = "#38414e" })
        ),
        MapTypeStyle(
            featureType = "road",
            elementType = "geometry.stroke",
            stylers = listOf(jso { color = "#212a37" })
        ),
        MapTypeStyle(
            featureType = "road",
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#9ca5b3" })
        ),
        MapTypeStyle(
            featureType = "road.highway",
            elementType = "geometry",
            stylers = listOf(jso { color = "#746855" })
        ),
        MapTypeStyle(
            featureType = "road.highway",
            elementType = "geometry.stroke",
            stylers = listOf(jso { color = "#1f2835" })
        ),
        MapTypeStyle(
            featureType = "road.highway",
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#f3d19c" })
        ),
        MapTypeStyle(
            featureType = "transit",
            elementType = "geometry",
            stylers = listOf(jso { color = "#2f3948" })
        ),
        MapTypeStyle(
            featureType = "transit.station",
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#d59563" })
        ),
        MapTypeStyle(
            featureType = "water",
            elementType = "geometry",
            stylers = listOf(jso { color = "#17263c" })
        ),
        MapTypeStyle(
            featureType = "water",
            elementType = "labels.text.fill",
            stylers = listOf(jso { color = "#515c6d" })
        ),
        MapTypeStyle(
            featureType = "water",
            elementType = "labels.text.stroke",
            stylers = listOf(jso { color = "#17263c" })
        )
    )
)

private const val RetroStyleString = """[
  {"elementType": "geometry", "stylers": [{"color": "#ebe3cd"}]},
  {"elementType": "labels.text.fill", "stylers": [{"color": "#523735"}]},
  {"elementType": "labels.text.stroke", "stylers": [{"color": "#f5f1e6"}]},
  {
    "featureType": "administrative",
    "elementType": "geometry.stroke",
    "stylers": [{"color": "#c9b2a6"}]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "geometry.stroke",
    "stylers": [{"color": "#dcd2be"}]
  },
  {
    "featureType": "administrative.land_parcel",
    "elementType": "labels.text.fill",
    "stylers": [{"color": "#ae9e90"}]
  },
  {
    "featureType": "landscape.natural",
    "elementType": "geometry",
    "stylers": [{"color": "#dfd2ae"}]
  },
  {
    "featureType": "poi",
    "elementType": "geometry",
    "stylers": [{"color": "#dfd2ae"}]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [{"color": "#93817c"}]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry.fill",
    "stylers": [{"color": "#a5b076"}]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [{"color": "#447530"}]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [{"color": "#f5f1e6"}]
  },
  {
    "featureType": "road.arterial",
    "elementType": "geometry",
    "stylers": [{"color": "#fdfcf8"}]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [{"color": "#f8c967"}]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry.stroke",
    "stylers": [{"color": "#e9bc62"}]
  },
  {
    "featureType": "road.highway.controlled_access",
    "elementType": "geometry",
    "stylers": [{"color": "#e98d58"}]
  },
  {
    "featureType": "road.highway.controlled_access",
    "elementType": "geometry.stroke",
    "stylers": [{"color": "#db8555"}]
  },
  {
    "featureType": "road.local",
    "elementType": "labels.text.fill",
    "stylers": [{"color": "#806b63"}]
  },
  {
    "featureType": "transit.line",
    "elementType": "geometry",
    "stylers": [{"color": "#dfd2ae"}]
  },
  {
    "featureType": "transit.line",
    "elementType": "labels.text.fill",
    "stylers": [{"color": "#8f7d77"}]
  },
  {
    "featureType": "transit.line",
    "elementType": "labels.text.stroke",
    "stylers": [{"color": "#ebe3cd"}]
  },
  {
    "featureType": "transit.station",
    "elementType": "geometry",
    "stylers": [{"color": "#dfd2ae"}]
  },
  {
    "featureType": "water",
    "elementType": "geometry.fill",
    "stylers": [{"color": "#b9d3c2"}]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [{"color": "#92998d"}]
  }]
"""