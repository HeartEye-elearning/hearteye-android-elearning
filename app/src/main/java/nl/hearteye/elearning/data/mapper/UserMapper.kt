package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.UserEntity
import nl.hearteye.elearning.data.model.User

object UserMapper {
    fun map(entity: UserEntity): User {
        return User(
            id = entity.id,
            firstName = entity.firstName,
            lastName = entity.lastName,
            email = entity.email,
            role = entity.role
        )
    }
}
