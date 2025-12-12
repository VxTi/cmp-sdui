package nl.vxti.sdui.screen

import nl.vxti.common.ServerComponent
import nl.vxti.common.RequiresAppVersion
import nl.vxti.common.screen.Screen
import nl.vxti.core.AppRequestContext

interface ScreenInstance {

    fun name(): String

    fun content(context: AppRequestContext): List<ServerComponent>;

    fun canProduceComponent(component: ServerComponent, context: AppRequestContext): Boolean {
        val annotation =
            component::class.annotations.find { it is RequiresAppVersion } as? RequiresAppVersion
                ?: return true;

        println("Component ${component.contentId} requires app version between ${annotation.min} and ${annotation.max}, current version is ${context.appVersion}")

        return context.appVersion >= annotation.min
                && context.appVersion <= annotation.max
    }

    fun createScreen(context: AppRequestContext): Screen {
        val filteredContent = content(context)
            .filter { component -> canProduceComponent(component, context) }

        return Screen(name(), filteredContent);
    }
}

