package com.globus.testkmm.base

import kotlinx.coroutines.CoroutineScope

expect open class ViewModel() {

    fun clear()

    fun getViewModelScope(): CoroutineScope

    protected open fun onCleared()

}