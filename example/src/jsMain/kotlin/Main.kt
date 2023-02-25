import com.chihsuanwu.maps.compose.web.GoogleMap
import com.chihsuanwu.maps.compose.web.state.CameraPosition
import com.chihsuanwu.maps.compose.web.state.LatLng
import com.chihsuanwu.maps.compose.web.state.rememberCameraPositionState
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        val cameraPositionState = rememberCameraPositionState(
            init = {
                position = CameraPosition(
                    center = LatLng(23.2, 120.5),
                    zoom = 8.0,
                )
            }
        )

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
                Text("Camera Position: ${cameraPositionState.position.center}")
                Button(
                    attrs = {
                        onClick {
                            val currentCenter = cameraPositionState.position.center
                            cameraPositionState.position = CameraPosition(
                                center = LatLng(currentCenter.lat + 0.1, currentCenter.lng + 0.1),
                                zoom = 8.0,
                            )
                        }
                    }
                ) {
                    Text("Click me")
                }
            }

            GoogleMap(
                id = "map",
                cameraPositionState = cameraPositionState,
                attrs = {
                    style {
                        width(100.percent)
                        flex(1) // Fill the remaining height
                        property("margin", "0 auto") // Center the map
                    }
                }
            )
        }
    }
}

