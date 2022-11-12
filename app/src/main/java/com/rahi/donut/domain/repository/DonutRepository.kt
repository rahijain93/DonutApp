package com.rahi.donut.domain.repository

import androidx.lifecycle.LiveData
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.model.ToppingsDetailsModel

/**
 * Created by Rahi on 11/November/2022
 */
interface DonutRepository {
    fun getDonutCount(): Int
    fun getToppingCount(id: Int): Int?
    fun getDonutById(id: Int): DonutListModel?
    fun getLiveDonutDat(): LiveData<List<DonutListModel>>?
    fun saveDonutData(master: List<DonutListModel>?): Boolean
    fun getAllToppingsById(id: Int): LiveData<List<ToppingsDetailsModel>>?
}