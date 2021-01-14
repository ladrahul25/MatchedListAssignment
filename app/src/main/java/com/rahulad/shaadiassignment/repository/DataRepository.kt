package com.rahulad.shaadiassignment.repository

import com.rahulad.roomdbpagingdemo.db.MatchedPersonDao
import com.rahulad.shaadiassignment.model.MatchedPersonData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DataRepository
@Inject
constructor(private val dao: MatchedPersonDao)  {

    suspend fun getMatchedData(): List<MatchedPersonData> = dao.getAllMatched()

    suspend fun insert(data : MatchedPersonData)  = withContext(Dispatchers.IO){
        dao.insert(data)
    }

    suspend fun update(data : MatchedPersonData)  = withContext(Dispatchers.IO){
        dao.update(data)
    }
}