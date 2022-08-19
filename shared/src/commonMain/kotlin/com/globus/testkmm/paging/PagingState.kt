package com.globus.testkmm.paging

sealed class PagingState<out V, out E> {
    class Success<V>(val content: V) : PagingState<V, Nothing>()
    class Error<E>(val error: E) : PagingState<Nothing, E>()
    object Loading : PagingState<Nothing, Nothing>()
    object NoData : PagingState<Nothing, Nothing>()
}
