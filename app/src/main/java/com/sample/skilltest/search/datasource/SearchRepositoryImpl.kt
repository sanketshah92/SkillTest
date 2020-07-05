package com.sample.skilltest.search.datasource

import com.sample.skilltest.network.NetworkHelper

class SearchRepositoryImpl(private val apiHelper: NetworkHelper) {
    suspend fun getSearchResult(page: String, query: String) =
        apiHelper.search(query = query, page = page)
}