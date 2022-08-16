package com.globus.testkmm.base

actual open class ViewModel {

    /**
     * This is the job for all coroutines started by this ViewModel.
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = SupervisorJob()

    /**
     * This is the main scope for all coroutines launched by this ViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by viewModelScope by calling viewModelJob.cancel()
     */
    // currently Default dispatcher is used https://github.com/kuuuurt/jokes-app-multiplatform/issues/6
    private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    actual fun clear() {
        onCleared()
    }

    actual fun getViewModelScope(): CoroutineScope {
        return viewModelScope
    }

    protected actual open fun onCleared() {
        viewModelJob.cancel()
    }
}