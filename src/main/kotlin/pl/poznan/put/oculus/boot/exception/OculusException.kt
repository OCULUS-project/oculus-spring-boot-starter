package pl.poznan.put.oculus.boot.exception

/**
 * The [Exception] class that should be used for exceptions in OCULUS.
 * Can be converted to standardised [ErrorMessage]
 */
abstract class OculusException (
        message: String
) : Exception(message) {

    /**
     * Returns [ErrorMessage] made from this error
     */
    fun errorMessage() = ErrorMessage(name, message!!, details)

    private val name: String
        get() = this::class.simpleName!!
                .replace("Exception", "")
                .replace(Regex("([a-z])([A-Z])"), "$1_$2")
                .toUpperCase()

    protected open val details: Map<String, Any>
        get() = emptyMap()
}

/**
 * Standardised error message for OCULUS. Should be used both for errors added to models and sent as error response
 *
 * @see OculusException
 */
data class ErrorMessage (
    /**
     * Name of the error. Should follow the pattern ([A-Z]+_[A-Z]+)+
     *
     * Generated automatically by [OculusException.errorMessage]
     */
    val error: String,
    /**
     * Message for the user
     */
    val message: String,
    /**
     * Details of the error for machine interpretation
     */
    val details: Map<String, Any>
)
