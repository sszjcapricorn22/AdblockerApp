package sszj.s.adblockerapp.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import sszj.s.adblockerapp.models.WebItem
import sszj.s.adblockerapp.repository.WebRepository
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    private val repository: WebRepository
) : ViewModel() {

    val websites = repository.websites
    val categories = repository.categories

    fun loadWebsites(category: String) {
        viewModelScope.launch {
            repository.getTweets(category)
        }
    }

    fun loadCategories() {
        viewModelScope.launch {
            repository.getCategories()
        }
    }

    fun pushNewWebsite(item: WebItem) {
        viewModelScope.launch {
            repository.addAndPushWebsite(item)
        }

    }
}
