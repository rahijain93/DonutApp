package com.rahi.donut.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rahi.donut.data.model.ToppingsDetailsModel
import com.rahi.donut.databinding.RowToppingsListBinding

class ToppingsAdapter : RecyclerView.Adapter<ToppingsAdapter.ToppingViewHolder>() {

    private var toppings = arrayListOf<ToppingsDetailsModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToppingViewHolder {
        val binding = RowToppingsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToppingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToppingViewHolder, position: Int) {
        val model = toppings[position]
        holder.bind(model)
    }

    override fun getItemCount() = toppings.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateToppings(toppingsList: ArrayList<ToppingsDetailsModel>) {
        toppings.clear()
        toppings.addAll(toppingsList)
        notifyDataSetChanged()
    }

    class ToppingViewHolder(
        val binding: RowToppingsListBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: ToppingsDetailsModel) {
            binding.model = data
        }
    }
}

