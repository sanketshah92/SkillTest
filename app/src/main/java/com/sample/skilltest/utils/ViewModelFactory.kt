package com.sample.skilltest.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sample.skilltest.db.CommentDatabase
import com.sample.skilltest.details.datasource.CommentRepository
import com.sample.skilltest.details.viewmodel.DetailsViewModel
import com.sample.skilltest.network.NetworkHelper
import com.sample.skilltest.search.datasource.SearchRepositoryImpl
import com.sample.skilltest.search.viewmodel.SearchViewModel

class ViewModelFactory(private val apiHelper: NetworkHelper,private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(SearchRepositoryImpl(apiHelper)) as T
        }else if (modelClass.isAssignableFrom(DetailsViewModel::class.java)){
            return DetailsViewModel(CommentRepository(CommentDatabase.getDatabase(context).wordDao())) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}