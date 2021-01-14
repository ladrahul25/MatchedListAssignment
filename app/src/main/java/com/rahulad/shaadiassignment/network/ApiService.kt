package com.rahulad.network

import com.rahulad.shaadiassignment.parsers.MatchResultsResponseParser
import retrofit2.http.GET

interface ApiService {

    @GET("?results=10")
    suspend fun getPostData(): MatchResultsResponseParser

}