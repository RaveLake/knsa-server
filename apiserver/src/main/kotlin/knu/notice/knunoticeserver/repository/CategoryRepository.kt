package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : JpaRepository<Category, Long>