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
import com.rahi.donut.databinding.ActivityMainBinding
import com.rahi.donut.ui.fragment.DonutListFragment
import com.rahi.donut.ui.fragment.ToppingDetailsFragment
import com.rahi.donut.ui.viewmodel.DonutViewModel
import com.rahi.donut.util.Result
import com.rahi.donut.util.listeners.DonutClickListener
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), DonutClickListener {
    val className = MainActivity::class.java.name
    lateinit var mainUi: ActivityMainBinding
    private var fragment: Fragment? = null
    private var tag = ""
    val mainVm by inject<DonutViewModel>()
    val app by inject<AppController>()
    private var fragmentManager: FragmentManager? = null

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


    override fun onBackPressed() {
        if (fragmentManager!!.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            finish()
        }
    }


    override fun onResume() {
        super.onResume()

        Log.e(className, "OnResume")
        if (fragment == null) {
            setUpFragment(DonutListFragment(), DonutListFragment.TAG)
        }

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

    override fun onDonutClick(id: Int) {
        setUpFragment(ToppingDetailsFragment(), ToppingDetailsFragment.TAG, id)
    }

    fun setUpFragment(fragment: Fragment, tag: String, id: Int = 0) {
        Log.e(className, "SetupFragment")
        val mBundle = Bundle()
        this.fragment = fragment
        this.tag = tag
        mBundle.putInt("Id", id)

        this.fragment?.arguments = mBundle
        fragmentManager?.beginTransaction()
            ?.replace(R.id.fragment, this.fragment!!)
            ?.addToBackStack(tag)
            ?.commit()
    }

    private fun setErrorText(errorMsg: String) {
        mainUi.errorTitle.visibility = if (errorMsg.isNotEmpty()) View.VISIBLE else View.GONE
        mainUi.errorTitle.text = errorMsg
    }
}