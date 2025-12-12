package nl.vxti.common

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import nl.vxti.common.components.ButtonComponent
import nl.vxti.common.components.ImageComponent
import nl.vxti.common.components.SearchBarComponent
import nl.vxti.common.components.SpacerComponent
import nl.vxti.common.components.TextComponent
import nl.vxti.common.components.container.CarouselContainerComponent
import nl.vxti.common.components.container.ListItemContainer
import nl.vxti.common.components.models.IComponent
import nl.vxti.common.components.models.ListItem
import nl.vxti.common.events.NavigationEvent
import nl.vxti.common.events.models.IEvent

val SDUIPolymorphicSerializer = SerializersModule {
    polymorphic(IComponent::class) {
        subclass(SpacerComponent::class, SpacerComponent.serializer())
        subclass(ButtonComponent::class, ButtonComponent.serializer())
        subclass(TextComponent::class, TextComponent.serializer())
        subclass(SearchBarComponent::class, SearchBarComponent.serializer())
        subclass(ImageComponent::class, ImageComponent.serializer())
        subclass(CarouselContainerComponent::class, CarouselContainerComponent.serializer())
        subclass(ListItemContainer::class, ListItemContainer.serializer())

        polymorphic(ListItem::class) {
            // To be implemented
        }

        polymorphic(IEvent::class) {
            subclass(NavigationEvent::class, NavigationEvent.serializer())
        }
    }

}