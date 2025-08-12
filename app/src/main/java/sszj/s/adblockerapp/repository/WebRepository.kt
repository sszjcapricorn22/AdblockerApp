package sszj.s.adblockerapp.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import sszj.s.adblockerapp.api.WebApi
import sszj.s.adblockerapp.models.WebItem
import javax.inject.Inject

class WebRepository @Inject constructor(private val webApi: WebApi) {

    private val _websites = MutableStateFlow<List<WebItem>>(emptyList())
    val websites: StateFlow<List<WebItem>> get() = _websites

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> get() = _categories


    suspend fun getWebsite(category: String) {
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
}