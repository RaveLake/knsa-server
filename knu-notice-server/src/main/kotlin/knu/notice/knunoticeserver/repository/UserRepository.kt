package knu.notice.knunoticeserver.repository

import knu.notice.knunoticeserver.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, String>