package com.rahulad.shaadiassignment.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rahulad.shaadiassignment.model.MatchedPersonData
import com.rahulad.shaadiassignment.parsers.MatchResultsResponseParser
import com.rahulad.shaadiassignment.repository.ApiRepository
import com.rahulad.shaadiassignment.repository.DataRepository
import com.rahulad.shaadiassignment.utils.Constants.GENDER_KEY_MALE
import com.rahulad.shaadiassignment.utils.Constants.GENDER_VAL_FEMALE
import com.rahulad.shaadiassignment.utils.Constants.GENDER_VAL_MALE
import kotlinx.coroutines.launch

class MyMatchesViewModel
@ViewModelInject
constructor(private val apiRepository: ApiRepository, private val dataRepository: DataRepository) :
    ViewModel() {
    val postDataMutableLiveData: MutableLiveData<List<MatchedPersonData>> = MutableLiveData()
    val postDataEmptyList: MutableLiveData<Boolean> = MutableLiveData()
    private lateinit var list: List<MatchedPersonData>
    var isConnected: Boolean = false

    fun fetchDataFromRepository(){
        viewModelScope.launch {
            //if table empty then call api else fetc data from DB
            list = if (dataRepository.getMatchedData().isNotEmpty()) {
                dataRepository.getMatchedData()
            } else {
                if (isConnected) {
                    val response: MatchResultsResponseParser = apiRepository.getPostData()
                    processApiData(response)
                } else {
                    postDataEmptyList.value = true
                    ArrayList()
                }
            }
            postDataMutableLiveData.value = list
        }
    }

    private fun processApiData(response: MatchResultsResponseParser?): List<MatchedPersonData> {
        val matchedList = ArrayList<MatchedPersonData>()
        if (null != response?.results && !response.results.isEmpty()) {
            for (i in response.results.indices) {
                val result = response.results.get(i)
                val gender = if (result.gender.isNotEmpty() && result.gender.equals(
                        GENDER_KEY_MALE,
                        false
                    )
                ) {
                    GENDER_VAL_MALE
                } else {
                    GENDER_VAL_FEMALE
                }
                val data = MatchedPersonData(
                    0,
                    gender,
                    result.name.first +" " +result.name.last,
                    result.location.city,
                    result.location.state,
                    result.dob.age,
                    result.picture.medium,
                    0
                )

                matchedList.add(data)
                viewModelScope.launch {
                    dataRepository.insert(data)
                }
            }
        }
        return matchedList
    }
}