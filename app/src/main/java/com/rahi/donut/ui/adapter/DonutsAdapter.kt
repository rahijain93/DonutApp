package com.rahi.donut.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.databinding.RowDonutListBinding
import com.rahi.donut.ui.viewmodel.DonutViewModel

class DonutsAdapter(
    private val vm: DonutViewModel,
    private val onClickEvent: (DonutListModel) -> Unit
) : RecyclerView.Adapter<DonutsAdapter.DonutViewHolder>() {

    private var donuts = arrayListOf<DonutListModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonutViewHolder {
        val binding = RowDonutListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DonutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DonutViewHolder, position: Int) {
        val model = donuts[position]
        holder.bind(vm, model)
        holder.binding.cvRoot.setOnClickListener {
            onClickEvent.invoke(model)
        }
    }

    override fun getItemCount() = donuts.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateDonuts(donutsList: ArrayList<DonutListModel>) {
        donuts.clear()
        donuts.addAll(donutsList)
        notifyDataSetChanged()
    }

    class DonutViewHolder(
        val binding: RowDonutListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(vm: DonutViewModel, data: DonutListModel) {
            binding.model = data
            binding.count.text = vm.getToppingCount(data.id).toString()
        }
    }
}