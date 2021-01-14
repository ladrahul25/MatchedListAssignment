package com.rahulad.network

import com.rahulad.shaadiassignment.parsers.MatchResultsResponseParser
import javax.inject.Inject


class ApiServiceImpl @Inject constructor(private val apiService: ApiService) {

    suspend fun getPostData(): MatchResultsResponseParser = apiService.getPostData()

}