package com.rahulad.roomdbpagingdemo.db

import androidx.room.*
import com.rahulad.shaadiassignment.model.MatchedPersonData

@Dao
interface MatchedPersonDao {
    @Query(value = "SELECT * FROM MatchedPersonData ORDER BY _id ASC")
    suspend fun getAllMatched(): List<MatchedPersonData>

    @Update
    suspend fun update(item: MatchedPersonData): Int

    @Insert
    suspend fun insert(item: MatchedPersonData): Long

}