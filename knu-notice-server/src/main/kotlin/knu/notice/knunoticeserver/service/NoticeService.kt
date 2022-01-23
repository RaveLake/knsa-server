package knu.notice.knunoticeserver.service

import knu.notice.knunoticeserver.domain.DefaultPage
import knu.notice.knunoticeserver.domain.NoticeAllUrl
import knu.notice.knunoticeserver.error.exception.NoticeNotFoundException
import knu.notice.knunoticeserver.dto.BaseResponse
import knu.notice.knunoticeserver.dto.NoticeDTO
import knu.notice.knunoticeserver.repository.NoticeCustomRepository
import knu.notice.knunoticeserver.repository.NoticeRepository
import knu.notice.knunoticeserver.util.makeBaseResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class NoticeService(
    private val noticeRepository: NoticeRepository,
    private val noticeCustomRepository: NoticeCustomRepository
) {

    fun getNoticeByPageNation(curPageNumber: Int): BaseResponse<NoticeDTO> {
        val results = noticeRepository.findAll(
            PageRequest.of(
                curPageNumber - 1,
                DefaultPage,
                Sort.by("createdAt").descending()
            )
        ).content.stream().map { notice -> NoticeDTO(notice) }.collect(
            Collectors.toList()
        )
        if (results.isEmpty())
            throw NoticeNotFoundException()
        return makeBaseResponse(noticeRepository.count(), curPageNumber, NoticeAllUrl, results)
    }

    fun getNoticeByDepartment(curPageNumber: Int, departments: String): BaseResponse<NoticeDTO> {
        val departmentList = departments.split("+", " ").toList()
        val noticeList = noticeRepository.getAllByCodeInOrderByCreatedAtDesc(departmentList)
        if (noticeList.isEmpty())
            throw NoticeNotFoundException()
        val noticeDTOList = noticeList.subList((curPageNumber - 1) * DefaultPage, curPageNumber * DefaultPage).stream()
            .map { notice -> NoticeDTO(notice) }.collect(Collectors.toList())
        return makeBaseResponse(noticeList.size.toLong(), curPageNumber, NoticeAllUrl, noticeDTOList)
    }

    fun getNoticeByDepartmentAndKeywords(
        curPageNumber: Int,
        departments: String,
        keywords: String
    ): BaseResponse<NoticeDTO> {
        val departmentList = departments.split("+", " ").toList()
        val keywordList = keywords.split("+", " ").toList()
        val noticeList = noticeCustomRepository.getAllByDepartmentAndKeyword(departmentList, keywordList)
        if (noticeList.isEmpty())
            throw NoticeNotFoundException()
        val noticeDTOList = noticeList.subList((curPageNumber - 1) * DefaultPage, curPageNumber * DefaultPage).stream()
            .map { notice -> NoticeDTO(notice) }.collect(Collectors.toList())
        return makeBaseResponse(noticeList.size.toLong(), curPageNumber, NoticeAllUrl, noticeDTOList)
    }
}