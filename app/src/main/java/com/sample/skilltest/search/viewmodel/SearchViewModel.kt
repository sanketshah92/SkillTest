package com.sample.skilltest.search.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.skilltest.network.Resource
import com.sample.skilltest.search.datasource.SearchRepositoryImpl
import com.sample.skilltest.search.model.Data
import com.sample.skilltest.search.model.LocalDataMapper
import com.sample.skilltest.search.model.SearchResults
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: SearchRepositoryImpl) : ViewModel() {
    private lateinit var data: SearchResults
    val searchDataObserver: MutableLiveData<Resource<SearchResults>> = MutableLiveData()

    fun doSearch(query: String, page: String) {
        searchDataObserver.value = Resource.loading(data = null)

        viewModelScope.launch {
            async {
                data = repo.getSearchResult(page = page, query = query)
            }.await()
        }.invokeOnCompletion {
            if (data != null) {
                if (data.status == 200)
                    searchDataObserver.value = Resource.success(data = data)
                else
                    searchDataObserver.value =
                        Resource.error(data = null, message = "Something went wrong, Try later!")
            } else {
                searchDataObserver.value =
                    Resource.error(data = null, message = "Something went wrong, Try later!")
            }
        }
    }

    fun mapDataToLocal(data: List<Data>, width: Int, height: Int): MutableList<LocalDataMapper> {
        val localData = mutableListOf<LocalDataMapper>()
        for (i in data.indices) {
            if (data[i].images != null) {
                if (data[i].images?.get(0)?.link?.trim()
                        ?.isNotBlank()!! && data[i].images?.get(0)?.type?.contains("image")!!
                ) {
                    val local = LocalDataMapper(
                        id = data[i].images?.get(0)?.id!!,
                        imageHeight = height,
                        imageWidth = width,
                        imageUrl = data[i].images?.get(0)?.link!!
                    )
                    localData.add(local)
                }
            }
        }
        return localData
    }

}