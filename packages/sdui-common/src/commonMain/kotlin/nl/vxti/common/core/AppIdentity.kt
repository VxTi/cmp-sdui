package nl.vxti.common.core

class AppIdentity {
    companion object {
        fun calculate(locale: Locale, version: Int): String {
            val input = "${locale.value}_$version"
            return input.hashCode().toString(16).uppercase()
        }
    }
}