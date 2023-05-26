package com.alinacoding.dormitoryfinal.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8081/"
    private var token: String = ""

    private val authInterceptor: Interceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
        if (token.isNotEmpty()) {
            newRequest.addHeader("Authorization", "Bearer $token")
        }
        chain.proceed(newRequest.build())
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(authInterceptor) // Add the authInterceptor here
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    val api: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun setToken(newToken: String) {
        this.token = newToken
    }
}
