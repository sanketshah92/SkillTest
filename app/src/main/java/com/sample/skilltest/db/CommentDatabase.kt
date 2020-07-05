package com.sample.skilltest.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sample.skilltest.details.datasource.CommentsDAO
import com.sample.skilltest.details.model.CommentData

@Database(entities = [CommentData::class], version = 1, exportSchema = false)
public abstract class CommentDatabase : RoomDatabase() {

    abstract fun wordDao(): CommentsDAO

    companion object {
        @Volatile
        private var INSTANCE: CommentDatabase? = null

        fun getDatabase(context: Context): CommentDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CommentDatabase::class.java,
                    "comment_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}