package knu.notice.knunoticeserver.controller

import knu.notice.knunoticeserver.service.PushAlarmService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/notice")
class PushAlarmController(
    private val pushAlarmService: PushAlarmService,
) {
    @PostMapping("/push")
    fun postPushAlarm() {
        pushAlarmService.sendMessage()
    }
}