package nl.vxti.server.middleware

import com.google.gson.Gson
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import nl.vxti.common.RequestHeader
import nl.vxti.server.ErrorResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.servlet.HandlerInterceptor

@Component
class AppVersionMiddleware : HandlerInterceptor {
    @Suppress("DefaultLocale")
    override fun preHandle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        handler: Any
    ): Boolean {
        val rawVersion = request.getHeader(RequestHeader.HEADER_APP_VERSION)

        if (rawVersion == null) return false

        try {
            val version = rawVersion.toInt()

            if (version < MINIMUM_APP_VERSION || version > MAXIMUM_APP_VERSION) {
                response.status = HttpServletResponse.SC_BAD_REQUEST
                response.writer.write(
                    Gson().toJson(
                        ErrorResponse(
                            String.format(
                                "Invalid app version provided. Please provide a " +
                                        "version between %d and %d",
                                MINIMUM_APP_VERSION,
                                MAXIMUM_APP_VERSION
                            )
                        )
                    )
                )

                return false
            }

            request.setAttribute(RequestHeader.ATTRIB_APP_VERSION, version)
        } catch (e: Exception) {
            log.warn("Invalid app version: {}", rawVersion)

            return false
        }

        return true
    }

    companion object {
        private val log = LoggerFactory.getLogger(AppVersionMiddleware::class.java)

        const val MINIMUM_APP_VERSION: Int = 1
        const val MAXIMUM_APP_VERSION: Int = 1
    }
}
