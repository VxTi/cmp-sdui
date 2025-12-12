package me.vxti.server.middleware

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration(proxyBeanMethods = false)
class MiddlewareConfiguration : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
        registry
            .addInterceptor(AppLocaleMiddleware())
            .order(0)
        registry
            .addInterceptor(AppVersionMiddleware())
            .order(1)
        registry
            .addInterceptor(AppContextMiddleware())
            .order(2)
    }
}
