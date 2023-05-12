import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.CameraPosition
import com.chihsuanwu.maps.compose.web.GoogleMap
import com.chihsuanwu.maps.compose.web.LatLng
import com.chihsuanwu.maps.compose.web.layers.*
import com.chihsuanwu.maps.compose.web.rememberCameraPositionState
import org.jetbrains.compose.web.css.margin
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Button
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text

private class MapLayerState {
    var bicyclingLayer: Boolean by mutableStateOf(false)
    var trafficLayer: Boolean by mutableStateOf(false)
    var transitLayer: Boolean by mutableStateOf(false)
    var heatmapLayer: Boolean by mutableStateOf(false)
    var kmlLayer: Boolean by mutableStateOf(false)
}

@Composable
fun LayerExample(apiKey: String) {

    val layerState: MapLayerState by remember { mutableStateOf(MapLayerState()) }

    MapLayerRow(layerState)
    CustomLayerRow(layerState)

    GoogleMap(
        apiKey = apiKey,
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition(
                center = LatLng(37.76, -122.44),
                zoom = 11.0,
            )
        },
        extra = "libraries=visualization"
    ) {
        if (layerState.bicyclingLayer) {
            BicyclingLayer()
        }
        if (layerState.trafficLayer) {
            TrafficLayer()
        }
        if (layerState.transitLayer) {
            TransitLayer()
        }
        if (layerState.heatmapLayer) {
            HeatmapLayer(
                data = listOf(
                    WeightedLocation(
                        LatLng(37.782, -122.447), 0.5
                    ),
                    WeightedLocation(
                        LatLng(37.782, -122.445), 0.7
                    ),
                    WeightedLocation(
                        LatLng(37.782, -122.443), 0.8
                    ),
                    WeightedLocation(
                        LatLng(37.782, -122.441), 0.9
                    ),
                    WeightedLocation(
                        LatLng(37.782, -122.439), 1.0
                    ),
                    WeightedLocation(
                        LatLng(37.782, -122.437), 0.7
                    ),
                    WeightedLocation(
                        LatLng(37.782, -122.435), 0.8
                    ),
                ),
            )
        }
        if (layerState.kmlLayer) {
            KMLLayer(
                url = "https://api.flickr.com/services/feeds/geo/?g=322338@N20&lang=en-us&format=feed-georss",
                onClick = {
                    console.log("KMLLayer clicked, ${it.featureData.author.name}")
                }
            )
        }
    }
}

@Composable
private fun MapLayerRow(
    state: MapLayerState
) {
    Div({
        style { margin(5.px) }
    }) {
        MapLayerToggleButton(
            text = "BicyclingLayer",
            state = state.bicyclingLayer,
            onClick = { state.bicyclingLayer = !state.bicyclingLayer }
        )
        MapLayerToggleButton(
            text = "TrafficLayer",
            state = state.trafficLayer,
            onClick = { state.trafficLayer = !state.trafficLayer }
        )
        MapLayerToggleButton(
            text = "TransitLayer",
            state = state.transitLayer,
            onClick = { state.transitLayer = !state.transitLayer }
        )
    }
}

@Composable
private fun CustomLayerRow(
    state: MapLayerState
) {
    Div({
        style { margin(5.px) }
    }) {
        MapLayerToggleButton(
            text = "HeatmapLayer",
            state = state.heatmapLayer,
            onClick = { state.heatmapLayer = !state.heatmapLayer }
        )
        MapLayerToggleButton(
            text = "KMLLayer",
            state = state.kmlLayer,
            onClick = { state.kmlLayer = !state.kmlLayer }
        )
    }
}

@Composable
private fun MapLayerToggleButton(
    text: String,
    state: Boolean,
    onClick: () -> Unit
) {
    Button({
        style { margin(5.px) }
        onClick { onClick() }
    }) {
        Text("$text: $state")
    }
}
