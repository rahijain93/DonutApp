package com.rahi.donut.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahi.donut.AppController
import com.rahi.donut.data.model.ToppingsDetailsModel
import com.rahi.donut.data.repo.DonutRepository
import com.rahi.donut.databinding.RowToppingsListBinding
import org.koin.core.KoinComponent
import org.koin.core.inject

class ToppingAdapter() : RecyclerView.Adapter<MyViewHolder1>(), KoinComponent {

    lateinit var context: Context
    private var inflater: LayoutInflater?=null
    lateinit var toppingInfoBinding: RowToppingsListBinding
    val app  by inject<AppController>()
    val repo by inject<DonutRepository>()

    companion object{
        var toppingList = arrayListOf<ToppingsDetailsModel>()
    }

    constructor(ctx: Context):this(){
        context = ctx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        toppingInfoBinding = RowToppingsListBinding.inflate(inflater?: LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder1(toppingInfoBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {

        val enquiryModel = toppingList[position]
        holder.bind1(enquiryModel)

    }


    override fun getItemCount() = toppingList.size

    fun setInfoModelArrayList(dataList: ArrayList<ToppingsDetailsModel>) {
        toppingList = dataList
        notifyDataSetChanged()
    }
}

class MyViewHolder1(itemView: RowToppingsListBinding) : RecyclerView.ViewHolder(itemView.root),
    KoinComponent {
    var surveyInfoBind: RowToppingsListBinding = itemView

    fun bind1(data: ToppingsDetailsModel) {
        surveyInfoBind.enquiryModel = data
    }


}