package com.rahi.donut.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.rahi.donut.R
import com.rahi.donut.databinding.ActivityMainBinding
import com.rahi.donut.ui.viewmodel.DonutViewModel
import com.rahi.donut.util.Result
import com.rahi.donut.util.extensions.isInternetAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<DonutViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        if (viewModel.getDonutCount() == 0) {
            if (isInternetAvailable()) {
                viewModel.getAllDonutApi()
            } else {
                setErrorText(getString(R.string.no_internet))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.result.observe(this) {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        if (it.status) {
                            binding.pbCircular.visibility = View.VISIBLE
                            setErrorText("Loading...")
                        } else {
                            binding.pbCircular.visibility = View.GONE
                            setErrorText("")
                        }
                    }
                    is Result.Success -> {
                        if (it.msg.startsWith("S")) {
                            Toast.makeText(this, it.msg, Toast.LENGTH_LONG).show()
                        } else {
                            setErrorText(it.msg)
                            binding.pbCircular.visibility = View.GONE
                        }
                    }
                    is Result.Error.RecoverableError -> {
                        setErrorText(it.exception.message!!)
                        binding.pbCircular.visibility = View.GONE
                    }
                    is Result.Error.NonRecoverableError -> {
                        setErrorText(it.exception.message!!)
                        binding.pbCircular.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun setErrorText(errorMsg: String) {
        binding.errorTitle.visibility = if (errorMsg.isNotEmpty()) View.VISIBLE else View.GONE
        binding.errorTitle.text = errorMsg
    }
}