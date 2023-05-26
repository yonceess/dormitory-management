package com.alinacoding.dormitoryfinal

import android.app.Application
import android.content.SharedPreferences
import android.util.JsonReader
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.alinacoding.dormitoryfinal.module.*
import com.alinacoding.dormitoryfinal.network.ApiService
import com.alinacoding.dormitoryfinal.network.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream


class LoginViewModel(application: Application) : AndroidViewModel(application) {


    val toastMessage = mutableStateOf<String?>(null)
    private val api: ApiService = RetrofitInstance.api

    private val KEY_TOKEN = "token"
    private lateinit var encryptedSharedPreferences: SharedPreferences

    init {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        encryptedSharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            application.applicationContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun authenticateUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = api.authenticateUser(AuthenticationRequest(email, password))
                if (response.isSuccessful) {
                    val authenticationResponse = response.body()
                    if (authenticationResponse != null) {
                        handleLoginSuccess(authenticationResponse.token)
                    } else {
                        handleLoginFailure()
                    }
                } else {
                    handleLoginFailure()
                }
            } catch (e: Exception) {
                handleLoginFailure()
            }
        }
    }

    private val _navigateToEmptyPage = MutableStateFlow(false)
    val navigateToEmptyPage: StateFlow<Boolean> = _navigateToEmptyPage

    private fun handleLoginSuccess(token: String) {
        encryptedSharedPreferences.edit().putString(KEY_TOKEN, token).apply()
        RetrofitInstance.setToken(token)
        _navigateToEmptyPage.value = true

        val storedToken = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        Log.d("StoredToken", "JWT Token: $storedToken")
    }

    fun resetNavigation() {
        _navigateToEmptyPage.value = false
    }

    private fun handleLoginFailure() {
        toastMessage.value = "Login failed. Please try again."
    }

    fun clearToastMessage() {
        toastMessage.value = null
    }




    val _userProfile = mutableStateOf<User?>(null)
    val userProfile: State<User?> = _userProfile

    fun getUserProfile() {
        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("UserProfile", "No token found in SharedPreferences")
            //
            return
        }

        viewModelScope.launch {
            try {
                val response = api.getProfileAndroid()
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        handleProfileSuccess(user)
                    } else {
                        handleProfileFailure()
                    }
                } else {
                    handleProfileFailure()
                }
            } catch (e: Exception) {
                handleProfileFailure()
            }
        }
    }


    private fun handleProfileSuccess(user: User) {
        _userProfile.value = user

        Log.d("User Profile", user.toString())

        Log.d("User Profile", "Firstname: ${user.firstname}")
        Log.d("User Profile", "Dormitory: ${user.dormitory}")
        Log.d("User Profile", "Apartment: ${user.apartment}")
        Log.d("User Profile", "Room: ${user.room}")
        Log.d("User Profile", "Email: ${user.email}")
    }

    private fun handleProfileFailure() {
        // Handle the failure case
        _userProfile.value = null
    }



    private val _itemsData = mutableStateOf<Items?>(null)
    val items: State<Items?> = _itemsData

    fun createComplaint(
        fullName: String,
        description_dorm: String,
        problem: String,
        phone: String,
        email: String
    ) {
        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("CreateComplaint", "No token found in SharedPreferences")
            return
        }

        val request = ItemsRequest(fullName, description_dorm, problem, phone, email)

        viewModelScope.launch {
            try {
                val response = api.createItemAndroid(request)
                if (response.isSuccessful) {
                    val createItemResponse = response.body()
                    if (createItemResponse != null) {
                        val message = createItemResponse.message
                        val newItem = Items(name = message)
                        handleCreateItemSuccess(newItem)
                    } else {
                        handleCreateItemFailure()
                    }
                } else {
                    handleCreateItemFailure()
                }
            } catch (e: Exception) {
                handleCreateItemFailure()
            }
        }
    }


    private fun handleCreateItemSuccess(item: Items) {
        _itemsData.value = item
        val message = item.name
        Log.d("CreateItem", "Item created successfully. Message: $message")
    }


    private fun handleCreateItemFailure() {
        _itemsData.value = null
    }


    private val _absenceData = mutableStateOf<Forms?>(null)
    val forms: State<Forms?> = _absenceData

    fun createAbsence(
        fullName: String,
        reason: String,
        date: String,
        phone: String,
        address: String
    ) {
        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("CreateAbsence", "No token found in SharedPreferences")
            return
        }

        val request = FormsRequest(fullName, date, reason, phone, address)

        viewModelScope.launch {
            try {
                val response = api.createAbsenceAndroid(request)
                if (response.isSuccessful) {
                    val createItemResponse = response.body()
                    if (createItemResponse != null) {
                        val message = createItemResponse.message
                        val newForm = Forms(name = message)
                        handleCreateFormsSuccess(newForm)
                    } else {
                        handleCreateFormsFailure()
                    }
                } else {
                    handleCreateFormsFailure()
                }
            } catch (e: Exception) {
                handleCreateFormsFailure()
            }
        }
    }


    private fun handleCreateFormsSuccess(forms: Forms) {
        _absenceData.value = forms
        val message = forms.name
        Log.d("CreateAbsence", "Item created successfully. Message: $message")
    }


    private fun handleCreateFormsFailure() {
        _absenceData.value = null
    }



    private val _userItems = mutableStateOf<List<Items>?>(null)
    val userItems: State<List<Items>?> = _userItems

    fun getUserItems() {
        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("UserItems", "No token found in SharedPreferences")
            return
        }

        viewModelScope.launch {
            try {
                val response = api.getUserItems()
                if (response.isSuccessful) {
                    val items = response.body()
                    if (items != null) {
                        handleUserItemsSuccess(items)
                    } else {
                        handleUserItemsFailure()
                    }
                } else {
                    handleUserItemsFailure()
                }

            } catch (e: Exception) {
                handleUserItemsFailure()
            }
        }
    }


    private fun handleUserItemsSuccess(items: List<Items>) {
        _userItems.value = items
    }

    private fun handleUserItemsFailure() {
        _userItems.value = null
    }


    private val _newsArticles = MutableStateFlow<List<News>>(emptyList())
    val newsArticles: StateFlow<List<News>> = _newsArticles.asStateFlow()

    var currentPage = 0

    val isLoading = MutableStateFlow(true)

    fun fetchAndLoadNews() {

        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("CreateAbsence", "No token found in SharedPreferences")
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = api.getNewsFeed(currentPage)
                if (response.isSuccessful) {
                    val newsList = response.body()
                    if (newsList != null) {
                        _newsArticles.value = newsList
                        Log.d("Log: LoginViewModel", "News Articles: ${_newsArticles.value}")
                        //currentPage++
                    } else {
                        handleNewsFetchFailure()
                    }
                } else {
                    handleNewsFetchFailure()
                }
                isLoading.value = false
            } catch (e: Exception) {
                handleNewsFetchFailure()
                isLoading.value = false
            }
        }
    }

    private fun handleNewsFetchFailure() {
        // Handle the failure case
        _newsArticles.value = listOf()
    }

    private val _newsItem = MutableStateFlow<News?>(null)
    val newsItem: StateFlow<News?> = _newsItem.asStateFlow()

    fun fetchNewsItem(id: Int) {
        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("CreateAbsence", "No token found in SharedPreferences")
            return
        }

        viewModelScope.launch {
            isLoading.value = true
            try {
                val response = api.getNewsItem(id)
                if (response.isSuccessful) {
                    val news = response.body()
                    if (news != null) {
                        _newsItem.value = news
                    } else {
                        handleNewsItemFetchFailure()
                    }
                } else {
                    handleNewsItemFetchFailure()
                }
                isLoading.value = false
            } catch (e: Exception) {
                handleNewsItemFetchFailure()
                isLoading.value = false
            }
        }
    }

    private fun handleNewsItemFetchFailure() {
        // Handle the failure case
        _newsItem.value = null
    }



    fun downloadDocFile(filePath: String) {


        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("DocFile", "No token found in SharedPreferences")
            return
        }

        viewModelScope.launch {
            try {
                val response = api.downloadFile(filePath, "Bearer $token")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        saveFileToStorage(responseBody, getFileName(filePath))
                    } else {
                        handleFileDownloadFailure()
                    }
                } else {
                    handleFileDownloadFailure()
                }
            } catch (e: Exception) {
                handleFileDownloadFailure()
            }
        }
    }


    private fun getFileName(filePath: String): String {
        return filePath.substring(filePath.lastIndexOf("/") + 1)
    }

    private fun saveFileToStorage(responseBody: ResponseBody, filename: String) {
        val directory = getApplication<Application>().getExternalFilesDir(null)
        val file = File(directory, filename)

        try {
            val inputStream = responseBody.byteStream()
            val outputStream = FileOutputStream(file)

            inputStream.copyTo(outputStream)

            outputStream.flush()
            outputStream.close()
            inputStream.close()
        } catch (e: Exception) {
            handleFileDownloadFailure()
        }
    }

    private fun handleFileDownloadFailure() {
        Log.e("FileDownload", "Failed to download file")
    }


    //temporary test
    private val _imageStream = MutableStateFlow<InputStream?>(null)
    val imageStream: StateFlow<InputStream?> = _imageStream.asStateFlow()

// ...

    fun downloadImageFile(filename: String) {
        val token = encryptedSharedPreferences.getString(KEY_TOKEN, null)
        if (token != null) {
            RetrofitInstance.setToken(token)
        } else {
            Log.e("ImageFile", "No token found in SharedPreferences")
            return
        }

        viewModelScope.launch {
            try {
                val response = api.getImageFile(filename, "Bearer $token")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _imageStream.value = responseBody.byteStream()
                    } else {
                        handleFileDownloadFailure()
                    }
                } else {
                    handleFileDownloadFailure()
                }
            } catch (e: Exception) {
                handleFileDownloadFailure()
            }
        }
    }
}

