import androidx.compose.runtime.*
import app.softwork.routingcompose.HashRouter
import app.softwork.routingcompose.Router
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
                    fontSize(2.em)
                    marginBottom(20.px)
                }
            }
        ) {
            Text("Hello, Google Map Compose Web!!")
        }

        HashRouter(initPath = "/") {
            if (setApiKeyClicked) {
                route("/") {
                    Navigator()
                }
                route("/map_example") {
                    MapExample(apiKey = apiKey)
                }
                route("/drawing_example") {
                    DrawingExample(apiKey = apiKey)
                }
                route("/layer_example") {
                    LayerExample(apiKey = apiKey)
                }
            } else {
                KeyRow(
                    apiKey = apiKey,
                    onInput = { apiKey = it },
                    onClick = { setApiKeyClicked = true }
                )
            }
        }
    }
}

@Composable
private fun KeyRow(
    apiKey: String,
    onInput : (String) -> Unit,
    onClick: () -> Unit
) {
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
                    onInput(event.value)
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
                    onClick()
                }
            }
        ) {
            Text("Set API Key")
        }
    }
}

@Composable
private fun Navigator() {
    Div({
        style {
            display(DisplayStyle.Flex)
            flexDirection(FlexDirection.Row)
            justifyContent(JustifyContent.Center)
            alignItems(AlignItems.Center)
            width(100.percent)
            height(100.percent)
        }
    }) {
        val router = Router.current
        NavCard("Google Map Example") {
            router.navigate("/map_example")
        }
        NavCard("Drawing Example") {
            router.navigate("/drawing_example")
        }
        NavCard("Layer Example") {
            router.navigate("/layer_example")
        }
    }
}

@Composable
private fun NavCard(
    text: String,
    onClick: () -> Unit
) {
    Button({
        style {
            width(240.px)
            height(160.px)
            margin(20.px)
            fontSize(1.5.em)
            backgroundColor(Color("#ffffff"))
            borderRadius(10.px)
            borderWidth(1.px)
            property("box-shadow", "0 0 5px 0 #222222")
        }
        onClick {
            onClick()
        }
    }) {
        Text(text)
    }
}
