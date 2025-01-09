package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.ProfilePictureEntity
import nl.hearteye.elearning.data.model.ProfilePicture

object ProfilePictureMapper {
    fun map(entity: ProfilePictureEntity): ProfilePicture {
        return ProfilePicture(
            base64Image = entity.base64Image,
            contentType = entity.contentType
        )
    }
}
