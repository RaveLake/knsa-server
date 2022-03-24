package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Keyword
import org.springframework.data.jpa.repository.JpaRepository

interface KeywordRepository : JpaRepository<Keyword, Long>