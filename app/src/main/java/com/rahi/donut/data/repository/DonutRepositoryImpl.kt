package com.rahi.donut.data.repository

import android.util.Log
import com.rahi.donut.data.local.DonutDao
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.model.ToppingsDetailsModel
import javax.inject.Inject

/**
 * Created by Rahi on 11/November/2022
 */
class DonutRepositoryImpl @Inject constructor(
    private val dao: DonutDao
) : DonutRepository {

    override fun getDonutCount() = dao.getDonutCount()

    override fun getToppingCount(id: Int) = dao.getToppingById(id).size

    override fun getDonutById(id: Int) = dao.getDonutById(id)

    override fun getLiveDonutDat() = dao.getAllDonuts()

    override fun saveDonutData(master: List<DonutListModel>?): Boolean {
        val bool = try {
            if (master != null) {
                master.forEach { donut ->
                    dao.insertDonutMaster(donut)
                    val top = arrayListOf<ToppingsDetailsModel>()
                    donut.toppings.forEach { topping ->
                        top.add(topping.copy(donutRefId = donut.id))
                    }
                    dao.insertToppingMaster(top)
                }
                true
            } else false
        } catch (ex: Exception) {
            Log.e("Exception", ex.message.toString())
            false
        }
        return bool
    }

    override fun getAllToppingsById(id: Int) = dao.getAllToppingsById(id)
}