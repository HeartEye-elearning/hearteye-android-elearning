package nl.hearteye.elearning.data.mapper

import nl.hearteye.elearning.data.entity.CommentEntity
import nl.hearteye.elearning.data.entity.DiscussionDetailEntity
import nl.hearteye.elearning.data.model.Comment
import nl.hearteye.elearning.data.model.DiscussionDetail

object DiscussionDetailMapper {
    fun map(entity: DiscussionDetailEntity): DiscussionDetail {
        return DiscussionDetail(
            id = entity.id,
            userId = entity.userId,
            title = entity.title,
            content = entity.content,
            category = entity.category,
            comments = entity.comments.map { commentEntity ->
                Comment(
                    id = commentEntity.id,
                    userId = commentEntity.userId,
                    parentCommentId = commentEntity.parentCommentId,
                    level = commentEntity.level,
                    content = commentEntity.content,
                    createdAt = commentEntity.createdAt,
                    updatedAt = commentEntity.updatedAt
                )
            },
            createdAt = entity.createdAt,
            updatedAt = entity.updatedAt
        )
    }

    fun mapComment(comment: Comment): CommentEntity {
        return CommentEntity(
            id = comment.id.toString(),
            userId = comment.userId,
            parentCommentId = comment.parentCommentId,
            level = comment.level,
            content = comment.content,
            createdAt = comment.createdAt,
            updatedAt = comment.updatedAt
        )
    }
}
