package knu.notice.knunoticeserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KnuNoticeServerApplication

fun main(args: Array<String>) {
    runApplication<KnuNoticeServerApplication>(*args)
}
