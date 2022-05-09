package knu.notice.receiveserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KnuNoticeReceiveAlarmApplication

fun main(args: Array<String>) {
    runApplication<KnuNoticeReceiveAlarmApplication>(*args)
}
