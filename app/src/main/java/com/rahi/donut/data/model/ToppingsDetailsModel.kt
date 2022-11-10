package com.rahi.donut.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class ToppingsDetailsModel(
    @PrimaryKey(autoGenerate = true)
    var pid: Int  = 0,

    @SerializedName("id")
    var toppingId : Int ? =null,

    @SerializedName("type")
    var toppingType : String? =null,

    var donutRefId: Int = 0
)
