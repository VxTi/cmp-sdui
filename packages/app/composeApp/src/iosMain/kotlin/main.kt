import androidx.compose.ui.window.ComposeUIViewController
import nl.vxti.App
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        App()
    }
}