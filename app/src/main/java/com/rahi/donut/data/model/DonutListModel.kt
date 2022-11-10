package com.rahi.donut.data.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class DonutListModel(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    var id: Int  = 0,

    @SerializedName("type")
    @Expose
    var type: String? = null,

    @SerializedName("name")
    @Expose
    var name: String? = null,

    @Ignore
    @SerializedName("topping")
    @Expose
    var toppings : List<ToppingsDetailsModel> = arrayListOf()
)