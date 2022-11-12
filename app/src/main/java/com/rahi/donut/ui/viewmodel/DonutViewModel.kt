package com.rahi.donut.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.remote.DonutApi
import com.rahi.donut.data.repository.DonutRepositoryImpl
import com.rahi.donut.domain.repository.DonutRepository
import com.rahi.donut.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class DonutViewModel @Inject constructor(
    private val api: DonutApi,
    val repository: DonutRepository
) : ViewModel(),
    CoroutineScope {

    val output = MutableLiveData<Result<String>>()
    val result: LiveData<Result<String>>
        get() = output

    var donutData: LiveData<List<DonutListModel>> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    fun getAllDonutApi() {
        output.value = Result.Loading(true)
        launch(coroutineContext) {
            api.getAllDonut().enqueue(object : Callback<List<DonutListModel>> {
                override fun onResponse(call: Call<List<DonutListModel>>, response: Response<List<DonutListModel>>) {
                    output.value = Result.Loading(false)
                    try {
                        if (response.isSuccessful) {
                            repository.saveDonutData(response.body()).let {
                                output.value = Result.Success(
                                    when {
                                        it -> "Success"
                                        else -> "Failure"
                                    }
                                )
                            }
                        } else {
                            output.value = Result.Error.RecoverableError(Exception(response.errorBody()?.string()))
                        }
                    } catch (ex: Exception) {
                        output.value = Result.Error.RecoverableError(Exception(ex.message))
                    }
                }

                override fun onFailure(call: Call<List<DonutListModel>>, t: Throwable) {
                    Log.e(TAG, t.message.toString())
                    output.value = Result.Loading(false)
                    output.value = Result.Error.NonRecoverableError(Exception(t.message))
                }
            })
        }
    }

    fun getDonutMaster() {
        donutData = repository.getLiveDonutDat()!!
    }

    fun getDonutCount() = repository.getDonutCount()

    fun getToppingCount(id: Int) = repository.getToppingCount(id)

    fun getAllToppingsById(id: Int) = repository.getAllToppingsById(id)

    fun getDonutById(id: Int) = repository.getDonutById(id)

    companion object {
        const val TAG = "DonutViewModel"
    }
}