package com.sample.skilltest.details.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.skilltest.details.datasource.CommentRepository
import com.sample.skilltest.details.model.CommentData
import com.sample.skilltest.network.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class DetailsViewModel(private val repository: CommentRepository) : ViewModel() {
    val comments: MutableLiveData<Resource<List<CommentData>>> = MutableLiveData()
    private val commentData = mutableListOf<CommentData>()

    fun getComments(id: String) {
        comments.value = Resource.loading(data = null)
        viewModelScope.launch {
            async {
                commentData.addAll(repository.getComments(id))
            }.await()
        }.invokeOnCompletion {
            comments.value = Resource.success(data = commentData)
        }
    }

    fun addComments(comment: String, commentId: String) {
        viewModelScope.launch {
            async {
                repository.insertComment(CommentData(c_id = commentId, comment = comment, id = 0))
            }.await()
        }.invokeOnCompletion {
            getComments(commentId)
        }
    }
}