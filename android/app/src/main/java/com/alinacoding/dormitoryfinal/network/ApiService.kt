package com.alinacoding.dormitoryfinal.network


import com.alinacoding.dormitoryfinal.module.*
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("api/v1/auth/authenticateUser")
    suspend fun authenticateUser(@Body authRequest: AuthenticationRequest): Response<AuthenticationResponse>

    @GET("user/api/profileAndroid")
    suspend fun getProfileAndroid(): Response<User>

    @POST("user/api/v1/createItemAndroid")
    suspend fun createItemAndroid(@Body request: ItemsRequest): Response<CreateItemResponse>

    @POST("user/api/v1/createAbsenceAndroid")
    suspend fun createAbsenceAndroid(@Body request: FormsRequest): Response<CreateAbsenceResponse>

    @GET("user/api/v1/userItemsAndroid")
    suspend fun getUserItems(): Response<List<Items>>

    @GET("file/api/user/allNewsAndroid/{pageNo}")
    suspend fun getNewsFeed(@Path("pageNo") pageNo: Int): Response<List<News>>

    @GET("file/api/user/newsAndroid/{id}")
    suspend fun getNewsItem(@Path("id") id: Int): Response<News>

    @Streaming
    @GET
    suspend fun downloadFile(@Url fileUrl: String, @Header("Authorization") token: String): Response<ResponseBody>

    @Streaming
    @GET("file/api/user/filesAndroid/{filename}")
    suspend fun getImageFile(@Path("filename") filename: String, @Header("Authorization") token: String): Response<ResponseBody>
}
