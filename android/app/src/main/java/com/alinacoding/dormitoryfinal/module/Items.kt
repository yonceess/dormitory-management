package com.alinacoding.dormitoryfinal.module

import com.google.gson.annotations.SerializedName

data class Items(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("descriptionDorm")
    val descriptionDorm: String? = null,

    @SerializedName("problem")
    val problem: String? = null,

    @SerializedName("phone")
    val phone: String? = null,

    @SerializedName("email")
    val email: String? = null
)
