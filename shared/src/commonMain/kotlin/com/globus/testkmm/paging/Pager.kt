package com.globus.testkmm.paging

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class Pager<Item>(
    private val scope: CoroutineScope,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val dataSource: PagingDataSource<Item>,
    private val compareItems: (Item, Item) -> Boolean,
    private val pagingConfig: PagingConfig = PagingConfig()
) {

    private val _dataFlow =
        MutableStateFlow<PagingState<List<Item>, Throwable>>(PagingState.NoData)
    val dataFlow = _dataFlow.asSharedFlow()

    private val resultPagingList = MutableStateFlow<List<Item>>(emptyList())

    fun initLoad() {
        scope.launch(dispatcher) {
            setLoadingState()
            try {
                val result = dataSource.loadItemsList(
                    pageNumber = pagingConfig.pageNumber,
                    pageSize = pagingConfig.pageSize
                )
                resultPagingList.emit(result)
                setSuccessState(result)
            } catch (e: Exception) {
                setErrorState(e)
            }
        }
    }

    fun loadNextPage() {
        scope.launch(dispatcher) {
            setLoadingState()
            val oldList = resultPagingList.value
            val pageSize = pagingConfig.pageSize

            try {
                val result = dataSource.loadItemsList(
                    pageNumber = countPageNumber(pageSize),
                    pageSize = pageSize
                )
                val filteredList = result.filter { resultItem ->
                    val comparedItem = oldList.firstOrNull { filterItem ->
                        compareItems(resultItem, filterItem)
                    }
                    comparedItem == null
                }

                val newList = oldList.plus(filteredList)
                resultPagingList.emit(newList)
                setSuccessState(newList)
            } catch (e: Exception) {
                setErrorState(e)
            }
        }
    }

    private fun countPageNumber(pageSize: Int): Int {
        val currentListSize = resultPagingList.value.size
        if (pageSize == 0) return 1
        val floatValue = currentListSize / pageSize.toFloat()
        return if (floatValue < currentListSize / pageSize) {
            floatValue.toInt() + 2
        } else floatValue.toInt() + 1
    }

    private suspend fun setSuccessState(list: List<Item>) =
        _dataFlow.emit(PagingState.Success(list))

    private suspend fun setErrorState(error: Throwable) = _dataFlow.emit(PagingState.Error(error))

    private suspend fun setLoadingState() = _dataFlow.emit(PagingState.Loading)
}