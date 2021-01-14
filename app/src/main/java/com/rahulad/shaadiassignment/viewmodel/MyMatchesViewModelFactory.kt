package com.rahulad.shaadiassignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rahulad.shaadiassignment.repository.ApiRepository
import com.rahulad.shaadiassignment.repository.DataRepository

class MyMatchesViewModelFactory(private val apiRepository: ApiRepository, private val dataRepository: DataRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MyMatchesViewModel(apiRepository, dataRepository) as T
    }
}