package nl.vxti.sdui.screen.models

import nl.vxti.common.RequiresAppVersion
import nl.vxti.common.components.models.IComponent
import nl.vxti.common.screen.Screen
import nl.vxti.core.AppRequestContext

interface IScreen {

    fun name(): String

    fun content(context: AppRequestContext): List<IComponent>;

    fun canProduceComponent(component: IComponent, context: AppRequestContext): Boolean {
        val annotation =
            component::class.annotations.find { it is RequiresAppVersion } as? RequiresAppVersion
                ?: return true;

        println("Component ${component.contentId} requires app version between ${annotation.min} and ${annotation.max}, current version is ${context.appVersion}")

        return context.appVersion >= annotation.min
                && context.appVersion <= annotation.max
    }

    fun create(context: AppRequestContext): Screen {
        val filteredContent = content(context)
            .filter { component -> canProduceComponent(component, context) }

        return Screen(name(), filteredContent);
    }
}