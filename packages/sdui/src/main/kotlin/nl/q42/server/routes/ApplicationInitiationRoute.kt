package me.vxti.server.routes

import me.vxti.common.RequestHeader
import me.vxti.common.ScreenResponse
import me.vxti.core.AppRequestContext
import me.vxti.sdui.ScreenRegistry
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RestController

@RestController
class ApplicationInitiationRoute(private val registry: ScreenRegistry) {

    @GetMapping(
        path = [ROUTE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun handler(
        @RequestAttribute(RequestHeader.ATTRIB_APP_CONTEXT) context: AppRequestContext
    ): ScreenResponse? {
        log.info(
            "Application initiated - Locale: {} - Version: {}", context.locale,
            context.appVersion,
        )

        val screen = registry.defaultScreen(context);

        return ScreenResponse(screen, tabs = ScreenRegistry.SCREEN_TABS);
    }

    companion object {
        const val ROUTE: kotlin.String = "/"

        private val log = LoggerFactory.getLogger(ApplicationInitiationRoute::class.java);
    }
}
