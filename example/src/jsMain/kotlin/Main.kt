import androidx.compose.runtime.*
import com.chihsuanwu.maps.compose.web.*
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

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
            center = LatLng(23.2, 120.5),
            zoom = 8.0,
        )
    }

    var polyline by remember {
        mutableStateOf(
            listOf(
                LatLng(23.2, 120.5),
                LatLng(21.2, 120.2),
                LatLng(25.5, 122.2),
                LatLng(23.2, 120.5),
            )
        )
    }

    Div(
        attrs = {
            style {
                margin(10.px)
            }
        }
    ) {
        Text("Camera Position: ${cameraPositionState.position.center}")
        Button(
            attrs = {
                onClick {
//                            val currentCenter = cameraPositionState.position.center
//                            cameraPositionState.position = CameraPosition(
//                                center = LatLng(currentCenter.lat + 0.1, currentCenter.lng + 0.1),
//                                zoom = 8.0,
//                            )
                    polyline = emptyList()
//                                listOf(
//                                LatLng(23.2, 120.5),
//                                LatLng(23.6, 120.6),
//                                LatLng(22.4, 120.2),
//                                LatLng(23.5, 120.8),
//                            )
                }
            }
        ) {
            Text("Click me")
        }
    }

    Div(
        attrs = {
            style {
                width(100.percent)
                flex(1) // Fill the remaining height
                property("margin", "0 auto") // Center the map
            }
        }
    ) {
        GoogleMap(
            apiKey = apiKey,
            cameraPositionState = cameraPositionState,
            attrs = {
                style {
                    width(500.px)
                    height(500.px)
                }
            }
        ) {
            if (polyline.isNotEmpty()) {
//            Polyline(
//                points = polyline,
//                color = "#FFBB22",
//                width = 5,
//            )
                Polygon(
                    points = polyline,
                    fillColor = "#FFBB22",
                    strokeColor = "#FF0000",
                    strokeWidth = 5,
                )
            }
        }
    }

}

