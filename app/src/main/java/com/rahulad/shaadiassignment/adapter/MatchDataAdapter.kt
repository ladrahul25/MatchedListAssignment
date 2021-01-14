package com.rahulad.shaadiassignment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rahulad.shaadiassignment.R
import com.rahulad.shaadiassignment.model.MatchedPersonData
import com.rahulad.shaadiassignment.repository.ApiRepository
import com.rahulad.shaadiassignment.repository.DataRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MatchDataAdapter(
    private var matchedDataList: ArrayList<MatchedPersonData>,
    private val listener : OnItemClickListener,
    private val dataRepository: DataRepository
) : RecyclerView.Adapter<MatchDataAdapter.PostDataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostDataViewHolder {
        return PostDataViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.row_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PostDataViewHolder, position: Int) {
        val matchedItemData = matchedDataList[position]
        holder.title.text = matchedItemData.name
        holder.address.text = matchedItemData.city + ", "+ matchedItemData.state
        holder.age.text = matchedItemData.age.toString() + " yrs"
        Glide.with(holder.itemView.getContext())
            .load(matchedItemData.photo)
            .centerCrop()
            .placeholder(R.drawable.ic_shaadi_splash_logo)
            .into(holder.photo)
        if (matchedItemData.status == 1){
            holder.connect.visibility = View.GONE
            holder.reject.visibility = View.VISIBLE
        } else if (matchedItemData.status == 2){
            holder.connect.visibility = View.VISIBLE
            holder.reject.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = matchedDataList.size

    inner class PostDataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val title: TextView = itemView.findViewById(R.id.name)
        val photo : ImageView = itemView.findViewById(R.id.photo)
        val address: TextView = itemView.findViewById(R.id.address)
        val age : TextView = itemView.findViewById(R.id.age)
        val connect : Button = itemView.findViewById(R.id.connect)
        val reject : Button = itemView.findViewById(R.id.reject)

        init {
            connect.setOnClickListener(this)
            reject.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION){ //helps if that row is deleted then this condition will handle
                when(v?.id){
                    R.id.connect -> {
                        matchedDataList[position].status = 1
                        GlobalScope.launch { dataRepository.update(matchedDataList[position]) }
                        connect.visibility = View.GONE
                        reject.visibility = View.VISIBLE
                    }
                    R.id.reject ->{
                        matchedDataList[position].status = 2
                        GlobalScope.launch { dataRepository.update(matchedDataList[position]) }
                        connect.visibility = View.VISIBLE
                        reject.visibility = View.GONE
                    }
                }
                listener.onItemClick(position)
            }
        }
    }

    fun setData(matchedDataList: ArrayList<MatchedPersonData>) {
        this.matchedDataList = matchedDataList
        notifyDataSetChanged()
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}