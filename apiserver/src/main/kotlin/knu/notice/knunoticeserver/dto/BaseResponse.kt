package knu.notice.knunoticeserver.dto

class BaseResponse<T>(val count: Long, val next: String?, val previous: String?, val results: List<T>)