package nl.vxti.core

import cmp_sdui.app.composeapp.generated.resources.Res
import cmp_sdui.app.composeapp.generated.resources.app_locale
import cmp_sdui.app.composeapp.generated.resources.app_version
import kotlinx.coroutines.runBlocking
import nl.vxti.common.core.AppIdentity
import nl.vxti.common.core.Locale
import org.jetbrains.compose.resources.getString

class AppInstance(val version: Int, val locale: Locale) {
    val identity: String = AppIdentity.calculate(locale, version);

    companion object {
        fun fromConfig() = runBlocking {
            AppInstance(
                version = getString(Res.string.app_version).toIntOrNull() ?: 1,
                locale = Locale.from(getString(Res.string.app_locale)),
            )
        }
    }
}
