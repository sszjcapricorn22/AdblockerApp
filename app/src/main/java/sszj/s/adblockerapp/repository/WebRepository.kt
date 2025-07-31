package sszj.s.adblockerapp.repository

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import sszj.s.adblockerapp.api.WebApi
import sszj.s.adblockerapp.models.WebItem
import sszj.s.adblockerapp.models.WebsiteWrapper
import sszj.s.adblockerapp.utils.Constants
import javax.inject.Inject

class WebRepository @Inject constructor(private val webApi: WebApi) {

    private val _websites = MutableStateFlow<List<WebItem>>(emptyList())
    val websites: StateFlow<List<WebItem>> get() = _websites

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> get() = _categories

    suspend fun getTweets(category: String) {
        val result = webApi.getWebsite("Websites[?(@.category==\"$category\")]")
        if (result.isSuccessful && result.body() != null) {
            _websites.emit(result.body()!!)
        }
    }

    companion object {
        const val MASTER_API_KEY = "\$2a\$10\$AlgrxOC.o0SfU2tigI89QuxyNnVpGIluUUqrNP9259Kg3cx6TPMza"
    }

    suspend fun getCategories() {
        val result = webApi.getCategories()
        if (result.isSuccessful && result.body() != null) {
            _categories.emit(result.body()!!)
        }
    }
    suspend fun addAndPushWebsite(webItem: WebItem) {
        try {
            val currentList = _websites.value.toMutableList()
            currentList.add(webItem)

            val body = WebsiteWrapper(websites = currentList)
            Log.d("Test123", "Sending updated body: $body")

//            val response = webApi.updateBin(
//                masterKey = "Bearer ${Constants.MASTER_KEY}",
//                binId = Constants.BIN_ID,
//                body = body
//            )
            val response = webApi.updateBin(
                masterKey = Constants.MASTER_KEY, // Just pass the key string, not with "Bearer"
                body = body
            )
            if (response.isSuccessful) {
                _websites.emit(currentList)
                Log.d("Test123", "✅ Website successfully pushed to JSONBin.")
            } else {
                val errorText = response.errorBody()?.string()
                Log.e(
                    "Test123",
                    "❌ Failed to push to JSONBin. Code: ${response.code()}, Error: $errorText"
                )
            }
        } catch (e: Exception) {
            Log.e("Test123", "❌ Exception occurred while updating JSONBin: ${e.localizedMessage}")
        }
    }}


/* suspend fun addAndPushWebsite(webItem: WebItem) {
     try {
         val currentList = _websites.value.toMutableList()
         currentList.add(webItem)

         val updatedBody = mapOf("Websites" to currentList)
         Log.d("Test123", "Request Body: $updatedBody")

//            val response = webApi.updateBin(
//                masterKey = "Bearer ${Constants.MASTER_KEY}",
//                binId = Constants.BIN_ID,
//                body = updatedBody
       val      body = WebsiteWrapper(websites = currentList)
         val response = webApi.updateBin("Bearer ${Constants.MASTER_KEY}", Constants.BIN_ID, body)

//            val response = webApi.updateBin(
//                masterKey = "Bearer ${Constants.MASTER_KEY}", // Make sure "Bearer" is included
//                binId = Constants.BIN_ID,
//                body = updatedBody
//
//            )

         if (response.isSuccessful) {
             _websites.emit(currentList)
             Log.d("Test123", "Website added and pushed to JSONBin")
         } else {
             Log.e("Test123", "Failed to update JSONBin: ${response.code()} - ${response.errorBody()?.string()}")
         }
     } catch (e: Exception) {
         Log.e("Test123", "Exception while updating bin: ${e.message}")
     }
 }

}
*/