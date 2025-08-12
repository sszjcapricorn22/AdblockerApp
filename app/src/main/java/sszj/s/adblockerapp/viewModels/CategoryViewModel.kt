package sszj.s.adblockerapp.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import sszj.s.adblockerapp.models.WebItem
import sszj.s.adblockerapp.repository.WebRepository
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(private val repository: WebRepository) : ViewModel() {

    val categories: StateFlow<List<String>>
        get() = repository.categories

    init {
        viewModelScope.launch {
            repository.getCategories()
        }
    }
}
