package knu.notice.knunoticeserver.util

import knu.notice.knunoticeserver.domain.DefaultPage
import knu.notice.knunoticeserver.domain.ServerUrl
import knu.notice.knunoticeserver.dto.BaseResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.ceil

fun <T> makeBaseResponse(count: Long, curPageNumber: Int, url: String, results: List<T>): BaseResponse<T> {
    val maxNumber = ceil(count / DefaultPage.toDouble()).toInt()
    val next = if (curPageNumber < maxNumber) ServerUrl + url + "?page=" + (curPageNumber + 1) else null
    val previous = if (curPageNumber - 1 > 0) ServerUrl + url + "?page=" + (curPageNumber - 1) else null
    return BaseResponse(count, next, previous, results)
}

inline fun <reified T> T.getLogger(): Logger = LoggerFactory.getLogger(T::class.java)
