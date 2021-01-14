package com.rahulad.shaadiassignment.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * status
 * 0 -> no action
 * 1 -> connected
 * 2 -> rejected
 * */


@Entity
data class MatchedPersonData(
    @PrimaryKey(autoGenerate = true) val _id: Int,
    @ColumnInfo(name = "gender") var gender: String,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "city") var city: String,
    @ColumnInfo(name = "state") var state: String,
    @ColumnInfo(name = "age") var age: Int,
    @ColumnInfo(name = "photo") var photo: String,
    @ColumnInfo(name = "status") var status: Int
) {

}