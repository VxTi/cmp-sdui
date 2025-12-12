package me.vxti.common

import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
val SDUIPolymorphicSerializer = SerializersModule {
    polymorphic(ServerComponent::class) {
        subclass(SpacerComponent::class, SpacerComponent.serializer())
        subclass(ButtonComponent::class, ButtonComponent.serializer())
        subclass(TextComponent::class, TextComponent.serializer())
        subclass(SearchBarComponent::class, SearchBarComponent.serializer())
        subclass(ImageComponent::class, ImageComponent.serializer())
        subclass(ScrollableContainer::class, ScrollableContainer.serializer())
        subclass(ListItemContainer::class, ListItemContainer.serializer())

        polymorphic(ListItem::class) {
            subclass(TransactionListItem::class, TransactionListItem.serializer())
        }

        polymorphic(Event::class) {
            subclass(NavigationEvent::class, NavigationEvent.serializer())
        }
    }

}