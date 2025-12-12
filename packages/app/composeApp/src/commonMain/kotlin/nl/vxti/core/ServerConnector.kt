package nl.vxti.core

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import nl.vxti.common.RequestHeader
import nl.vxti.common.SDUIPolymorphicSerializer
import nl.vxti.common.ScreenResponse
import nl.vxti.common.core.QueryParameter
import nl.vxti.common.core.ServerRoute
import nl.vxti.local.getLocalDevelopmentUri

class ServerConnector(private val appInstance: AppInstance) {
    val serverBaseUrl = getLocalDevelopmentUri();

    val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                isLenient = true
                serializersModule = SDUIPolymorphicSerializer
            })
        }
    }

    suspend inline fun <reified T> fetch(
        appInstance: AppInstance,
        path: String,
        params: List<Pair<String, String>> = listOf()
    ): T? where T : Any {
        return try {
            httpClient.get("$serverBaseUrl$path") {
                params.forEach { parameter(it.first, it.second) }
                header(RequestHeader.HEADER_APP_LOCALE, appInstance.locale.value)
                header(RequestHeader.HEADER_APP_VERSION, appInstance.version)
                header(RequestHeader.HEADER_APP_IDENTITY, appInstance.identity)
            }.body<T>()
        } catch (exception: Exception) {
            println("An error occurred whilst attempting to deserialize response: ${exception.message}")
            return null
        }
    }

    suspend fun fetchScreen(screenId: String, callback: (ScreenResponse?) -> Unit) {
        try {
            fetch<ScreenResponse>(
                this.appInstance,
                ServerRoute.SCREEN,
                listOf(Pair(QueryParameter.SCREEN_IDENTIFIER, screenId))
            ).let { response -> callback(response) }
        } catch (exception: Exception) {
            println("An error occurred whilst attempting to deserialize response: ${exception.message}")
        }
    }
}