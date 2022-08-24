package com.globus.testkmm.paging

internal class DefaultPagingDataSource<Item>(private val itemsFetcher: suspend (Int, Int) -> List<Item>) :
    PagingDataSource<Item> {
    override suspend fun loadItemsList(pageNumber: Int, pageSize: Int): List<Item> =
        itemsFetcher.invoke(pageNumber, pageSize)
}

interface PagingDataSource<Item> {
    suspend fun loadItemsList(pageNumber: Int, pageSize: Int): List<Item>
}