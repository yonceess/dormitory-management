package com.alinacoding.dormitoryfinal.module

data class FormsRequest(
    var name: String? = null,
    var date: String? = null,
    var address: String? = null,
    var reason: String? = null,
    var phone: String? = null,
    var email: String? = null
)
