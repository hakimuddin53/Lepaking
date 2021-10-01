package com.example.lepaking.network.model

import com.google.gson.annotations.SerializedName

/**
 *Created by jhcheng on 2/17/2020.
 */
class CustomerOrderModel {
    @SerializedName("no")
    val no : String = ""
    @SerializedName("OrderID")
    val OrderID : String = ""
    @SerializedName("orderStatus")
    val orderStatus : String = ""
    @SerializedName("CustName")
    val CustName : String = ""
    @SerializedName("CustTele")
    val CustTele : String = ""
    @SerializedName("OrderTime")
    val OrderTime : String = ""
    @SerializedName("Paid")
    val Paid : String = ""
    @SerializedName("Type")
    val Type : String = ""
    @SerializedName("PrepareTime")
    val PrepareTime : String = ""
    @SerializedName("TotalPrice")
    val TotalPrice : String = ""
    @SerializedName("Remark")
    val Remark : String = ""
}