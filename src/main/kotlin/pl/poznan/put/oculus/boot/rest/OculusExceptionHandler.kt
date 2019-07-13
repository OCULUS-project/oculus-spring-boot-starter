package pl.poznan.put.oculus.boot.rest

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.poznan.put.oculus.boot.exception.OculusException

/**
 * Provides default handler for [OculusException]
 */
@ControllerAdvice
class OculusExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [ OculusException::class ])
    protected fun defaultHandleOculusException(e: OculusException, request: WebRequest) = handleExceptionInternal(
        e, e.errorMessage(), HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request
    )
}
