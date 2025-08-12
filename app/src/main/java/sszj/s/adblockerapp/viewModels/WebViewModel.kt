package sszj.s.adblockerapp.viewModels

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
}
