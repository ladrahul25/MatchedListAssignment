package com.rahulad.shaadiassignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rahulad.shaadiassignment.R
import com.rahulad.shaadiassignment.adapter.MatchDataAdapter
import com.rahulad.shaadiassignment.databinding.ActivityMyMatchesBinding
import com.rahulad.shaadiassignment.model.MatchedPersonData
import com.rahulad.shaadiassignment.repository.ApiRepository
import com.rahulad.shaadiassignment.repository.DataRepository
import com.rahulad.shaadiassignment.utils.ConnectionLiveData
import com.rahulad.shaadiassignment.viewmodel.MyMatchesViewModel
import com.rahulad.shaadiassignment.viewmodel.MyMatchesViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MyMatchesActivity : AppCompatActivity(), MatchDataAdapter.OnItemClickListener {

    @Inject lateinit var apiRepository: ApiRepository
    @Inject lateinit var dataRepository: DataRepository

    private lateinit var myMatchesViewModel: MyMatchesViewModel
    private lateinit var matchDataAdapter: MatchDataAdapter
    private lateinit var connectionLiveData: ConnectionLiveData
    private lateinit var binding: ActivityMyMatchesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //view binding
        binding = ActivityMyMatchesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        myMatchesViewModel = ViewModelProviders.of(this, MyMatchesViewModelFactory(apiRepository, dataRepository)).get(MyMatchesViewModel::class.java)

        //network listener
        connectionLiveData = ConnectionLiveData(this)

        //populate data from server/db
        myMatchesViewModel.fetchDataFromRepository()

        //observe list
        myMatchesViewModel.postDataMutableLiveData.observe(this, Observer {
            matchDataAdapter.setData(it as ArrayList<MatchedPersonData>)
            binding.progressBar.visibility = View.GONE
        })

        //observe network changes
        connectionLiveData.observe(this) {
            myMatchesViewModel.isConnected = it
            if (it) {
                myMatchesViewModel.fetchDataFromRepository()
                binding.noDataImage.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.noDataMessage.visibility = View.GONE
            }
        }

        //observe empty list event
        myMatchesViewModel.postDataEmptyList.observe(this, {
            binding. progressBar.visibility = View.GONE
            binding.noDataImage.visibility = View.VISIBLE
            binding.noDataMessage.visibility = View.VISIBLE
        })
    }

    private fun initRecyclerView() {
        matchDataAdapter = MatchDataAdapter(ArrayList(), this, dataRepository)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MyMatchesActivity)
            adapter = matchDataAdapter
        }
    }

    override fun onItemClick(position: Int) {
        //TODO handle clicks if needed
    }
}