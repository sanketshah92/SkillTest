package com.sample.skilltest.details.datasource

import com.sample.skilltest.details.model.CommentData

class CommentRepository(private val dao: CommentsDAO) {

    suspend fun insertComment(commentData: CommentData): Long = dao.addComment(commentData)
    suspend fun getComments(id: String): List<CommentData> = dao.getComments(id)
}