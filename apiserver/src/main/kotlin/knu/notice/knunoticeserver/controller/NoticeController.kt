package knu.notice.knunoticeserver.controller

import knu.notice.knunoticeserver.domain.Category
import knu.notice.knunoticeserver.dto.BaseResponse
import knu.notice.knunoticeserver.dto.NoticeDTO
import knu.notice.knunoticeserver.repository.CategoryRepository
import knu.notice.knunoticeserver.service.NoticeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notice")
class NoticeController(private val noticeService: NoticeService, private val categoryRepository: CategoryRepository) {

    @GetMapping("/all")
    fun getAll(
        @RequestParam("page", required = false, defaultValue = "1") pageNumber: Int,
        @RequestParam("target") target: String,
        @RequestParam("q", required = false, defaultValue = "") keywords: String
    ): BaseResponse<NoticeDTO> {
        return if (target == "all")
            noticeService.getNoticeByPageNation(pageNumber)
        else if (keywords == "")
            noticeService.getNoticeByDepartment(pageNumber, target)
        else
            noticeService.getNoticeByDepartmentAndKeywords(pageNumber, target, keywords)

    }

    @GetMapping("/list")
    fun getList(): List<Category> {
        return categoryRepository.findAll()
    }
}