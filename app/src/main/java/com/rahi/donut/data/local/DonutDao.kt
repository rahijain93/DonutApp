package com.rahi.donut.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rahi.donut.data.model.DonutListModel
import com.rahi.donut.data.model.ToppingsDetailsModel

@Dao
interface DonutDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDonutMaster(donuts: DonutListModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToppingMaster(donuts: List<ToppingsDetailsModel>)

    @Query("select * from donutlistmodel")
    fun getAllDonuts(): LiveData<List<DonutListModel>>

    @Query("select * from donutlistmodel where id=:id")
    fun getDonutById(id: Int): DonutListModel

    @Query("select Count(*) from donutlistmodel")
    fun getDonutCount(): Int

    @Query("select * from toppingsdetailsmodel where donutRefId =:id")
    fun getToppingById(id: Int): List<ToppingsDetailsModel>

    @Query("select * from toppingsdetailsmodel where donutRefId =:id")
    fun getAllToppingsById(id: Int): LiveData<List<ToppingsDetailsModel>>
}