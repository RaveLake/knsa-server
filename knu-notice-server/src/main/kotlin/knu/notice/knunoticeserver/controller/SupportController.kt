package knu.notice.knunoticeserver.controller

import knu.notice.knunoticeserver.dto.SupportDTO
import knu.notice.knunoticeserver.repository.SupportRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/support")
class SupportController(private val supportRepository: SupportRepository) {
    @GetMapping("/version")
    fun getVersion(): SupportDTO {
        return SupportDTO(supportRepository.getById(1))
    }
}