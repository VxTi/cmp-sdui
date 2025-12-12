package me.vxti.common.core

enum class Locale
    (val value: String) {
    NL_NL("nl-NL"),
    EN_US("en-US"),
    EN_UK("en-UK");

    companion object {
        fun from(value: String?, defaultLocale: Locale = NL_NL): Locale {
            for (locale in entries) {
                if (locale.value == value) return locale
            }

            return defaultLocale
        }
    }
}