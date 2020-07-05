package com.sample.skilltest.details.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sample.skilltest.details.model.CommentData

@Dao
interface CommentsDAO {
    @Query("SELECT * FROM comments WHERE c_id = :id")
    suspend fun getComments(id: String): List<CommentData>

    @Insert
    suspend fun addComment(commentData: CommentData): Long
}