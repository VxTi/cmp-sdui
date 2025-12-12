package me.vxti.common

class RequestHeader {
    companion object {
        const val ATTRIB_LOCALE: String = "locale"
        const val ATTRIB_APP_VERSION: String = "appVersion"
        const val ATTRIB_APP_CONTEXT: String = "context"
        const val ATTRIB_REQUIRES_APP_REVALIDATION: String = "requiresRevalidation"

        const val HEADER_APP_VERSION: String = "X-App-Version"
        const val HEADER_APP_LOCALE: String = "X-App-Locale"
        const val HEADER_APP_IDENTITY: String = "X-App-Identity"
    }
}