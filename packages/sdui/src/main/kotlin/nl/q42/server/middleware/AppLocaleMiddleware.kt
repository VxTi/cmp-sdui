package me.vxti.server.middleware

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import me.vxti.common.RequestHeader
import me.vxti.common.core.Locale
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AppLocaleMiddleware : HandlerInterceptor {
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val appLocale: Locale = Locale.from(
            request.getHeader(RequestHeader.HEADER_APP_LOCALE),
            DEFAULT_LOCALE
        )

        request.setAttribute(RequestHeader.ATTRIB_LOCALE, appLocale)

        return true
    }

    companion object {
        val DEFAULT_LOCALE: Locale = Locale.NL_NL
    }
}
