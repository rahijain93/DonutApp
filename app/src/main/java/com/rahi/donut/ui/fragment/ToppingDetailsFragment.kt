package com.rahi.donut.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rahi.donut.AppController
import com.rahi.donut.data.model.ToppingsDetailsModel
import com.rahi.donut.databinding.FragmentToppingListBinding
import com.rahi.donut.ui.adapter.ToppingAdapter
import com.rahi.donut.ui.viewmodel.DonutViewModel
import org.koin.android.ext.android.inject


class ToppingDetailsFragment : Fragment() {

    lateinit var ToppingAdapter: ToppingAdapter
    lateinit var ToppingUi: FragmentToppingListBinding
    val mainVm by inject<DonutViewModel>()
    val app by inject<AppController>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        ToppingUi = FragmentToppingListBinding.inflate(inflater, container, false)
        ToppingUi.donutModel = mainVm.getDonutById(arguments?.getInt("Id") ?: 0)
        return ToppingUi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ToppingAdapter = ToppingAdapter(this.requireContext())
        ToppingUi.rvList.adapter = ToppingAdapter
        val bundle = arguments
        val donutId = bundle?.getInt("Id") ?: 0
        mainVm.getAllToppingsById(donutId)?.observe(this.viewLifecycleOwner, Observer {
            if (it != null) {
                ToppingAdapter.setInfoModelArrayList(it as ArrayList<ToppingsDetailsModel>)
            }
        })
    }

    companion object {
        val TAG: String = ToppingDetailsFragment::class.simpleName.toString()
    }
}