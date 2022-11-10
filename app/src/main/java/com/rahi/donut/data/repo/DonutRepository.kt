package com.rahi.donut.data.repo

import android.util.Log
import com.rahi.donut.AppController
import com.rahi.donut.data.database.DonutsDatabase
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.model.ToppingsDetailsModel

class DonutRepository(val app: AppController, val database: DonutsDatabase) {

    fun saveDonutData(master: List<DonutListModel>?) : Boolean{
        val bool = try {
            if(master!=null) {
                master.forEach { donut ->
                    database.mainDao()?.insertDonutMaster(donut)
                    val top = arrayListOf<ToppingsDetailsModel>()
                    donut.toppings.forEach {
                        top.add(it.apply {
                            it.donutRefId = donut.id
                        })
                    }

                    top.forEach{
                        Log.e("Repo ${it.pid}", "Ref Id :" + it.donutRefId.toString())
                    }

                     database.mainDao()?.insertToppingMaster(top)
                    }
                    true
                }else false
        }catch (ex:Exception){
            Log.e("Exception",ex.message.toString())
            false
        }
        return bool
    }

    fun getLiveDonutDat() = database.mainDao()?.getAllDonuts()

    fun getDonutCount() = database.mainDao()?.getDonutCount()?:0

    fun getToppingCount(id : Int) = database.mainDao()?.getToppingById(id)?.size

    fun getAllToppingsById(id : Int) = database.mainDao()?.getAllToppingsById(id)

    fun getDonutById(id : Int) = database.mainDao()?.getDonutById(id)

}