import androidx.compose.runtime.Composable
import com.chihsuanwu.maps.compose.web.GoogleMap
import com.chihsuanwu.maps.compose.web.LatLng
import com.chihsuanwu.maps.compose.web.layers.HeatmapLayer
import com.chihsuanwu.maps.compose.web.layers.KMLLayer
import com.chihsuanwu.maps.compose.web.layers.WeightedLocation

@Composable
fun LayerExample(apiKey: String) {
    GoogleMap(
        apiKey = apiKey,
        extra = "libraries=visualization"
    ) {
//        HeatmapLayer(
//            data = listOf(
//                WeightedLocation(
//                    LatLng(37.782, -122.447), 0.5
//                ),
//                WeightedLocation(
//                    LatLng(37.782, -122.445), 0.7
//                ),
//                WeightedLocation(
//                    LatLng(37.782, -122.443), 0.8
//                ),
//                WeightedLocation(
//                    LatLng(37.782, -122.441), 0.9
//                ),
//                WeightedLocation(
//                    LatLng(37.782, -122.439), 1.0
//                ),
//                WeightedLocation(
//                    LatLng(37.782, -122.437), 0.7
//                ),
//                WeightedLocation(
//                    LatLng(37.782, -122.435), 0.8
//                ),
//            ),
//        )
        KMLLayer(
            url = "http://api.flickr.com/services/feeds/geo/?g=322338@N20&lang=en-us&format=feed-georss"
        )
    }
}