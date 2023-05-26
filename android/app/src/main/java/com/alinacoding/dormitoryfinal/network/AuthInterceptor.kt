package com.alinacoding.dormitoryfinal.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(private val token: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header("Authorization", "Bearer $token")
            .method(original.method, original.body)

        val request = requestBuilder.build()
        return chain.proceed(request)
    }
}
