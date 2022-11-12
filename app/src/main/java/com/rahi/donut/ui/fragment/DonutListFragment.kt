package com.rahi.donut.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.databinding.FragmentDonutListBinding
import com.rahi.donut.ui.adapter.DonutsAdapter
import com.rahi.donut.ui.viewmodel.DonutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DonutListFragment : Fragment() {

    lateinit var binding: FragmentDonutListBinding
    private lateinit var donutAdapter: DonutsAdapter
    private val viewModel by activityViewModels<DonutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getDonutMaster()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDonutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        setUpObserver()
    }

    private fun setUpAdapter() {
        donutAdapter = DonutsAdapter(viewModel) { model ->
            val direction = DonutListFragmentDirections.toFragmentToppings(model.id)
            findNavController().navigate(direction)
        }
        binding.rvList.adapter = donutAdapter
    }

    private fun setUpObserver() {
        viewModel.donutData.observe(viewLifecycleOwner) {
            if (it != null) {
                donutAdapter.updateDonuts(it as ArrayList<DonutListModel>)
            }
        }
    }
}