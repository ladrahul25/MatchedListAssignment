package com.rahulad.roomdbpagingdemo.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rahulad.shaadiassignment.model.MatchedPersonData


@Database(entities = arrayOf(MatchedPersonData::class), version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun matchedPersonDao(): MatchedPersonDao
}