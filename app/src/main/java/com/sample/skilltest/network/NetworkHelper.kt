package com.sample.skilltest.network

class NetworkHelper(private val apiService: ApiService) {

    suspend fun search(page:String,query:String) = apiService.search(page = Integer.parseInt(page),searchQuery = query)
}