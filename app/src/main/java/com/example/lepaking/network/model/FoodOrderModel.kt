package com.example.lepaking.network.model

import com.google.gson.annotations.SerializedName

/**
 *Created by jhcheng on 2/17/2020.
 */
class FoodOrderModel {
    @SerializedName("orderid")
    val orderid : String = ""
    @SerializedName("vendorid")
    val vendorid : String = ""
    @SerializedName("foodid")
    val foodid : String = ""
    @SerializedName("foodname")
    val foodname : String = ""
    @SerializedName("quantity")
    val quantity : String = ""
    @SerializedName("price")
    val price : String = ""
    @SerializedName("remark")
    val remark : String = ""
}