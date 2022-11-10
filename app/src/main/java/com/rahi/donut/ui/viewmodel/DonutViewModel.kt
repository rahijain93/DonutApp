package com.rahi.donut.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahi.donut.AppController
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.network.ApiCallInterface
import com.rahi.donut.data.repo.DonutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import com.rahi.donut.util.*
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.lang.Exception


class DonutViewModel(val app: AppController, val repo: DonutRepository) : ViewModel(), CoroutineScope {

    val output = MutableLiveData<Result<String>>()
    val api = app.getRetrofitClient().create(ApiCallInterface::class.java)
    var donutData : LiveData<List<DonutListModel>> ?= MutableLiveData()

    val result : LiveData<Result<String>>
        get() = output

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO


    fun getAllDonutApi(){
        output.value = Result.Loading(true)

        launch(coroutineContext) {
            api.getAllDonut().enqueue(object : Callback<List<DonutListModel>> {

                override fun onResponse(call: Call<List<DonutListModel>>, response: Response<List<DonutListModel>>) {
                    output.value = Result.Loading(false)
                    try {
                        if(response.isSuccessful){
                            //output.value = Result.SuccessMsg("Success")
                            repo.saveDonutData(response.body()).let {
                                Log.e("Status",it.toString() + " ")
                                if(it){
                                    output.value = Result.SuccessMsg("Success")
                                }else{
                                    output.value = Result.SuccessMsg("Failure")
                                }
                            }
                        }else{
                            Log.e("Call Else","Success")
                            output.value = Result.Error.RecoverableError(Exception(response.errorBody()?.string()))
                        }

                    }catch (ex : Exception){
                        Log.e("Exception",ex.toString())
                        output.value = Result.Error.RecoverableError(Exception(ex.message))
                    }
                }

                override fun onFailure(call: Call<List<DonutListModel>>, t: Throwable) {
                    Log.e("Call",t.message.toString())
                    output.value = Result.Loading(false)
                    output.value = Result.Error.NonRecoverableError(Exception(t.message))

                }
            })
        }
    }

    fun getDonutMaster(){
        donutData = repo.getLiveDonutDat()
    }

    fun getDonutCount() = repo.getDonutCount()

    fun getToppingCount(id : Int) = repo.getToppingCount(id)

    fun getAllToppingsById(id : Int) = repo.getAllToppingsById(id)

    fun getDonutById(id : Int) = repo.getDonutById(id)

}