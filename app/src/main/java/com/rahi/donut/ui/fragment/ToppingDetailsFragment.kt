package com.rahi.donut.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.rahi.donut.data.model.ToppingsDetailsModel
import com.rahi.donut.databinding.FragmentToppingListBinding
import com.rahi.donut.ui.adapter.ToppingsAdapter
import com.rahi.donut.ui.viewmodel.DonutViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ToppingDetailsFragment : Fragment() {

    lateinit var binding: FragmentToppingListBinding
    private lateinit var toppingAdapter: ToppingsAdapter
    private val args: ToppingDetailsFragmentArgs by navArgs()
    private val viewModel by activityViewModels<DonutViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentToppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpView()
        setUpAdapter()
        setUpObserver()
    }

    private fun setUpView() {
        binding.donutModel = viewModel.getDonutById(args.id)
    }

    private fun setUpAdapter() {
        toppingAdapter = ToppingsAdapter()
        binding.rvList.adapter = toppingAdapter
    }

    private fun setUpObserver() {
        viewModel.getAllToppingsById(args.id)?.observe(this.viewLifecycleOwner) {
            if (it != null) {
                toppingAdapter.updateToppings(it as ArrayList<ToppingsDetailsModel>)
            }
        }
    }
}