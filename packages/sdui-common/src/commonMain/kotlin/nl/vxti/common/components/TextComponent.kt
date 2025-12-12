package nl.vxti.common.components

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.vxti.common.components.models.ComponentType
import nl.vxti.common.components.models.IComponent

@Serializable
@SerialName(ComponentType.TEXT)
data class TextComponent(
    val text: String,
    val color: TextColor = TextColor.PRIMARY,
    var size: TextSize = TextSize.NORMAL,
    val formatting: TextFormatting = TextFormatting.NORMAL,
    override val contentId: String,
) : IComponent {
    override val _type: String = ComponentType.TEXT
}

enum class TextColor {
    PRIMARY,
    SECONDARY,
    TERTIARY,
    DISABLED,
    ERROR,
    SUCCESS,
    INFO,
    CUSTOM;
}

enum class TextFormatting {
    NORMAL,
    BOLD,
    ITALIC,
    UNDERLINE;
}

enum class TextSize {
    EXTRA_SMALL,
    SMALL,
    NORMAL,
    LARGE,
    EXTRA_LARGE,
}