package com.rahulad.shaadiassignment.repository

import com.rahulad.network.ApiServiceImpl
import com.rahulad.roomdbpagingdemo.db.MatchedPersonDao
import com.rahulad.shaadiassignment.parsers.MatchResultsResponseParser
import javax.inject.Inject

class ApiRepository
@Inject
constructor(private val apiServiceImpl: ApiServiceImpl)  {
    suspend fun getPostData(): MatchResultsResponseParser = apiServiceImpl.getPostData()
}