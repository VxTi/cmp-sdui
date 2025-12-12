import androidx.compose.ui.window.ComposeUIViewController
import me.vxti.App
import me.vxti.ViewController
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        App(ViewController())
    }
}