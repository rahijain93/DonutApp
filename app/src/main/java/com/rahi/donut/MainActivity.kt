package com.rahi.donut

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.rahi.donut.databinding.ActivityMainBinding
import com.rahi.donut.ui.fragment.DonutListFragment
import com.rahi.donut.ui.fragment.ToppingDetailsFragment
import com.rahi.donut.ui.viewmodel.DonutViewModel
import com.rahi.donut.util.Result
import com.rahi.donut.util.listeners.DonutClickListener
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    lateinit var mainUi: ActivityMainBinding
    val mainVm by inject<DonutViewModel>()
    val app by inject<AppController>()
    private var fragmentManager: FragmentManager? = null
    val navController : NavController by lazy {
        val navHostFragment: NavHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navHostFragment.navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainUi = DataBindingUtil.setContentView(this, R.layout.activity_main)
        fragmentManager = supportFragmentManager

        if (mainVm.getDonutCount() == 0) {
            if (app.isInternetAvailable()) {
                mainVm.getAllDonutApi()
            } else {
                setErrorText(getString(R.string.no_internet))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mainVm.result.observe(this, Observer {
            if (it != null) {
                when (it) {
                    is Result.Loading -> {
                        if (it.status) {
                            mainUi.pbCircular.visibility = View.VISIBLE
                            setErrorText("Loading")
                        } else {
                            mainUi.pbCircular.visibility = View.GONE
                            setErrorText("")
                        }
                    }
                    is Result.SuccessMsg -> {
                        if (it.msg.startsWith("S")) {
                            app.showToast(it.msg)
                        } else {
                            setErrorText(it.msg)
                        }
                    }
                    is Result.Error.RecoverableError -> {
                        setErrorText(it.exception.message!!)
                    }
                    is Result.Error.NonRecoverableError -> {
                        setErrorText(it.exception.message!!)
                    }
                    else -> {
                        setErrorText(getString(R.string.something_went_wrong))
                    }
                }
            }
        })
    }

    private fun setErrorText(errorMsg: String) {
        mainUi.errorTitle.visibility = if (errorMsg.isNotEmpty()) View.VISIBLE else View.GONE
        mainUi.errorTitle.text = errorMsg
    }
}