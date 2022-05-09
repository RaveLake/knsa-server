package knu.notice.knunoticeserver.service

import knu.notice.knunoticeserver.domain.User
import knu.notice.knunoticeserver.dto.UserDTO
import knu.notice.knunoticeserver.error.exception.BadRequestException
import knu.notice.knunoticeserver.error.exception.UserNotFoundException
import knu.notice.knunoticeserver.repository.UserRepository
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserById(id: String): UserDTO {
        try {
            return UserDTO(userRepository.getById(id))
        } catch (e: JpaObjectRetrievalFailureException) {
            throw UserNotFoundException()
        } catch (e: Exception) {
            throw BadRequestException()
        }
    }

    @Transactional
    fun saveNewUser(newUser: UserDTO): UserDTO {
        if (userRepository.findById(newUser.id).isPresent) {
            throw BadRequestException()
        } else {
            val result = userRepository.save(User(newUser))
            return UserDTO(result)
        }
    }

    @Transactional
    fun updateUser(updateUser: UserDTO): UserDTO {
        getUserById(updateUser.id)
        val existingUser = userRepository.getById(updateUser.id)
        existingUser.update(updateUser)
        val result = userRepository.save(existingUser)
        return UserDTO(result)
    }

    @Transactional
    fun deleteUserById(deleteUser: UserDTO) {
        getUserById(deleteUser.id)
        userRepository.deleteById(deleteUser.id)
    }
}