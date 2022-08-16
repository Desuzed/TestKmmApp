package com.globus.testkmm.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope

actual open class ViewModel: ViewModel() {

    actual fun clear() {
        onCleared()
    }

    actual override fun onCleared() {
        super.onCleared()
    }

    actual fun getViewModelScope(): CoroutineScope {
        return viewModelScope
    }
}
