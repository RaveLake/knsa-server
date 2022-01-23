package knu.notice.knunoticeserver.error

import knu.notice.knunoticeserver.error.exception.BadRequestException
import knu.notice.knunoticeserver.error.exception.DeviceNotFoundException
import knu.notice.knunoticeserver.error.exception.NoticeNotFoundException
import knu.notice.knunoticeserver.error.exception.UserNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionControllerAdvice {
    @ExceptionHandler(DeviceNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun deviceNotFound() {

    }

    @ExceptionHandler(
        BadRequestException::class,
        HttpMessageNotReadableException::class,
        MissingServletRequestParameterException::class
    )
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun badRequest() {

    }

    @ExceptionHandler(UserNotFoundException::class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    fun userNotFound() {

    }

    @ExceptionHandler(NoticeNotFoundException::class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    fun noticeNotFound() {

    }
}