package com.peterchege.paginatedapp.ui.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.peterchege.paginatedapp.api.PaginatedApi
import com.peterchege.paginatedapp.models.Product
import com.peterchege.paginatedapp.pagination.DefaultPaginator
import com.peterchege.paginatedapp.pagination.ProductsDataSource
import com.peterchege.paginatedapp.repositoryImpl.ProductRepositoryImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeScreenViewModel : ViewModel() {
    val api = PaginatedApi.instance



    private val repository = ProductRepositoryImpl(api = api)

    val productsPager = Pager(PagingConfig(pageSize = 20)) {
        ProductsDataSource(repository = repository)
    }.flow.cachedIn(viewModelScope)

    init {

    }

//    private val paginator = DefaultPaginator(
//        initialKey = _page,
//        onLoadUpdated = {
//            _isLoading.value = it
//            //state = state.copy(isLoading = it)
//        },
//        onRequest = { nextPage ->
//            Log.e("Next Page :",nextPage.value.toString())
//            delay(1000L)
//            Result.success(api.getAllProducts(limit = 10, offset = nextPage.value).products)
//
//        },
//        getNextKey = {
//            _page.value += 1
//            _page
//        },
//        onError = {
//            _isError.value = true
//            _msg.value = it?.localizedMessage ?: "An error occurred"
//
//        },
//        onSuccess = { items, newKey ->
//            addProducts(items = items)
//            _page.value = newKey.value
//            _endReached.value = items.isEmpty()
//
//        }
//    )

}