package knu.notice.knunoticeserver.service

import knu.notice.knunoticeserver.domain.DefaultPage
import knu.notice.knunoticeserver.domain.NoticeAllUrl
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
        val count = noticeRepository.count()
        val results = noticeRepository.findAll(
            PageRequest.of(
                curPageNumber - 1,
                DefaultPage,
                Sort.by("date").descending()
            )
        ).content.stream().map { notice -> NoticeDTO(notice) }.collect(
            Collectors.toList()
        )
        return makeBaseResponse(count, curPageNumber, NoticeAllUrl, results)
    }

    fun getNoticeByDepartment(curPageNumber: Int, departments: String): BaseResponse<NoticeDTO> {
        val departmentList = departments.split("+", " ").toList()
        val count = noticeRepository.getTotalSizeByCodeIn(departmentList)
        val noticeList = noticeRepository.getAllByCodeInOrderByDateDesc(
            departmentList,
            PageRequest.of(curPageNumber - 1, DefaultPage)
        ).stream().map { notice -> NoticeDTO(notice) }.collect(Collectors.toList())
        return makeBaseResponse(count, curPageNumber, NoticeAllUrl, noticeList)
    }

    fun getNoticeByDepartmentAndKeywords(
        curPageNumber: Int,
        departments: String,
        keywords: String
    ): BaseResponse<NoticeDTO> {
        val departmentList = departments.split("+", " ").toList()
        val keywordList = keywords.split("+", " ").toList()
        val count = noticeCustomRepository.getTotalSizeByDepartmentAndKeyword(departmentList, keywordList)
        val noticeList =
            noticeCustomRepository.getAllByDepartmentAndKeyword(departmentList, keywordList, curPageNumber - 1).stream()
                .map { notice -> NoticeDTO(notice) }.collect(Collectors.toList())
        return makeBaseResponse(count, curPageNumber, NoticeAllUrl, noticeList)
    }
}