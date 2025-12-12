package nl.vxti.common

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class RequiresAppVersion(
    /**
     * Minimum app version required to use the annotated component.
     * Default is 1, meaning the component is supported from the first version onwards.
     */
    val min: Int = 1,

    /**
     * Maximum app version that supports the annotated component.
     * Default is Int.MAX_VALUE, meaning no upper limit, ergo the component is supported in all future versions.
     */
    val max: Int = Int.Companion.MAX_VALUE
)
