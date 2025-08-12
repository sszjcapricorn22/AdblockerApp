package sszj.s.adblockerapp.viewModels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sszj.s.adblockerapp.models.WebItem
import sszj.s.adblockerapp.repository.WebRepository
import javax.inject.Inject

@HiltViewModel
class ItemViewModel @Inject constructor(
    private val repository: WebRepository, private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val tweets: StateFlow<List<WebItem>>
        get() = repository.websites

    init {
        viewModelScope.launch {
            val category = savedStateHandle.get<String>("category") ?: "Education"
            repository.getWebsite(category)
        }

    }
}