package com.maji.mvvm.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.maji.mvvm.databinding.ItemSubjectBinding
import com.maji.mvvm.model.Subject

class SubjectAdapter(var dataList: MutableList<Subject?>?) :RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object{
        @BindingAdapter("bindData")
        fun bindAdapter(recyclerView: RecyclerView, subjectList: MutableList<Subject?>?) : SubjectAdapter {
            val adapter = SubjectAdapter(subjectList)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
            recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL))
            return adapter
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(ItemSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false).root) {}
    }

    override fun getItemCount(): Int {
        return dataList?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var model = dataList?.get(position)
        val binding: ItemSubjectBinding? = DataBindingUtil.bind(holder.itemView)
        binding?.subject = model
    }

}