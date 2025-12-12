package nl.vxti.server.routes

import nl.vxti.common.RequestHeader
import nl.vxti.common.ScreenResponse
import nl.vxti.common.core.QueryParameter
import nl.vxti.common.core.ServerRoute
import nl.vxti.common.screen.Screen
import nl.vxti.core.AppRequestContext
import nl.vxti.core.exceptions.ScreenNotFoundException
import nl.vxti.sdui.ScreenRegistry
import org.slf4j.LoggerFactory
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestAttribute
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ScreenRoute(private val registry: ScreenRegistry) {
    private val log = LoggerFactory.getLogger(ScreenRoute::class.java)

    @GetMapping(path = [ServerRoute.SCREEN], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ScreenNotFoundException::class)
    fun handler(
        @RequestAttribute(RequestHeader.ATTRIB_APP_CONTEXT) context: AppRequestContext,
        @RequestParam(QueryParameter.SCREEN_IDENTIFIER) screenIdentifier: String
    ): ScreenResponse? {
        if (screenIdentifier.isEmpty()) {
            log.warn("Screen identifier is missing")
            return null
        }

        log.info("Incoming screen request for ID \"{}\"", screenIdentifier)

        val screen: Screen =
            registry.getByIdentifier(screenIdentifier, context) ?: throw ScreenNotFoundException(
                String.format(
                    "Unable to retrieve screen with identifier %s",
                    screenIdentifier
                )
            )

        return ScreenResponse(screen);
    }
}
