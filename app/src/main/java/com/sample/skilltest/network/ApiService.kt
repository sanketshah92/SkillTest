package com.sample.skilltest.network

import com.sample.skilltest.search.model.SearchResults
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("search/{page}")
    suspend fun search(
        @Path("page") page: Int,
        @Query("q") searchQuery: String,
        @Header("Authorization") header: String = "Client-ID 137cda6b5008a7c"
    ): SearchResults
}