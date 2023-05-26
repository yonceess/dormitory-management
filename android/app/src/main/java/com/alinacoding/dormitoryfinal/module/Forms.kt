package com.alinacoding.dormitoryfinal.module

import com.google.gson.annotations.SerializedName

data class Forms(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("date")
    val date: String? = null,

    @SerializedName("address")
    val address: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("reason")
    val reason: String? = null,

    @SerializedName("email")
    val email: String? = null
)
