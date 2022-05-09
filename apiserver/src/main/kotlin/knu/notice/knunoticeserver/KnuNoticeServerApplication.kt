package knu.notice.knunoticeserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class KnuNoticeServerApplication

fun main(args: Array<String>) {
    runApplication<KnuNoticeServerApplication>(*args)
}
