package nl.vxti.core


enum class ScreenNavigationAction(val mask: Int) {
    PUSH(1),
    REPLACE(2),
    STORE(4),
    PUSH_AND_STORE(1 or 4),
    REPLACE_AND_STORE(2 or 4)
}
