package com.maji.mvvm.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maji.mvvm.R
import com.maji.mvvm.model.Subject

class SubjectAdapter(private val dataList: List<Subject?>?) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_subject, parent, false)
        return SubjectHolder(itemView)
    }

    override fun getItemCount(): Int {
        return dataList?.size?:0
    }

    override fun onBindViewHolder(holder2: RecyclerView.ViewHolder, position: Int) {
        val holder = holder2 as SubjectHolder
        var model = dataList?.get(position)

        holder.tvUrlName.text = model?.authorizations_url
        holder.tvCreatedTime.text = model?.create_time
    }

    class SubjectHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvUrlName: TextView = itemView.findViewById(R.id.tv_subject_url)
        val tvCreatedTime: TextView = itemView.findViewById(R.id.tv_subject_created_time)

    }
}