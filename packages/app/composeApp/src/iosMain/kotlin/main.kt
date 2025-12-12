import androidx.compose.ui.window.ComposeUIViewController
import nl.vxti.App
import nl.vxti.ViewController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        App(ViewController())
    }
}