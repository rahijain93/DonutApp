package com.rahi.donut.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.rahi.donut.AppController
import com.rahi.donut.MainActivity
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.databinding.FragmentDonutListBinding
import com.rahi.donut.ui.adapter.GenericAdapter
import com.rahi.donut.ui.viewmodel.DonutViewModel
import com.rahi.donut.util.listeners.DonutClickListener
import org.koin.android.ext.android.inject

class DonutListFragment : Fragment(), DonutClickListener {
    lateinit var MainAdapter: GenericAdapter
    lateinit var donutUi: FragmentDonutListBinding
    val mainVm by inject<DonutViewModel>()
    val app by inject<AppController> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVm.getDonutMaster()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        donutUi = FragmentDonutListBinding.inflate(inflater, container, false)
        MainAdapter = GenericAdapter(this.requireContext(), this)
        donutUi.rvList.adapter = MainAdapter
        return donutUi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainVm.donutData?.observe(this.viewLifecycleOwner, Observer {
            if(it!=null){
                MainAdapter.setInfoModelArrayList(it as ArrayList<DonutListModel>)
            }
        })
    }

    override fun onDonutClick(id: Int) {
        (activity as MainActivity).navController
            .navigate( DonutListFragmentDirections.actionFragmentDonutListToFragmentToppings(id.toString()))
    }
}