package com.rahi.donut.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahi.donut.AppController
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.repo.DonutRepository
import com.rahi.donut.databinding.RowDonutListBinding
import com.rahi.donut.ui.viewmodel.DonutViewModel
import com.rahi.donut.util.listeners.DonutClickListener
import org.koin.core.KoinComponent
import org.koin.core.inject

class GenericAdapter() : RecyclerView.Adapter<MyViewHolder>(),KoinComponent{

    lateinit var context: Context
    private var inflater: LayoutInflater ?=null
    lateinit var donutInfoBinding: RowDonutListBinding
    val app  by inject<AppController>()
    val repo by inject<DonutRepository>()

    companion object{
        var listener: DonutClickListener ?=null
        var donutList = arrayListOf<DonutListModel>()
    }

    constructor(ctx: Context, callback: DonutClickListener):this(){
        context = ctx
        listener = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        donutInfoBinding = RowDonutListBinding.inflate(inflater?:LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(donutInfoBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val enquiryModel = donutList[position]
        holder.bind(enquiryModel)
        holder.surveyInfoBind.mainView.setOnClickListener {
          listener?.onDonutClick(enquiryModel.id)
        }
    }


    override fun getItemCount() = donutList.size

    fun setInfoModelArrayList(dataList: ArrayList<DonutListModel>) {
        donutList = dataList
        notifyDataSetChanged()
    }

}

class MyViewHolder(itemView: RowDonutListBinding) : RecyclerView.ViewHolder(itemView.root),KoinComponent {
    var surveyInfoBind: RowDonutListBinding = itemView
    val mainVm by inject<DonutViewModel> ()

    fun bind(data: DonutListModel) {
        surveyInfoBind.enquiryModel = data
        surveyInfoBind.count.text = mainVm.getToppingCount(data.id).toString()
    }
}


