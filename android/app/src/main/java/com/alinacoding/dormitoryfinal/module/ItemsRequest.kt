package com.alinacoding.dormitoryfinal.module

data class ItemsRequest(
    var name: String? = null,
    var descriptionDorm: String? = null,
    var problem: String? = null,
    var phone: String? = null,
    var email: String? = null
)
