package com.peterchege.paginatedapp.ui.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.paginatedapp.api.PaginatedApi
import com.peterchege.paginatedapp.models.Product
import com.peterchege.paginatedapp.pagination.DefaultPaginator
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeScreenViewModel : ViewModel() {
    val api = PaginatedApi.instance

    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products : State<List<Product>> = _products

    private val _isError = mutableStateOf(false)
    val isError: State<Boolean> =_isError

    private val _errorMsg = mutableStateOf("")
    val errorMsg: State<String> =_errorMsg

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> =_isLoading

    private val _page = mutableStateOf(0)
    val page: State<Int> =_page


    private val _endReached = mutableStateOf(false)
    val endReached: State<Boolean> =_endReached

    private val _msg = mutableStateOf("")
    val msg: State<String> = _msg

    init {
        getProducts()
    }

    private val paginator = DefaultPaginator(
        initialKey = _page,
        onLoadUpdated = {
            _isLoading.value = it
            //state = state.copy(isLoading = it)
        },
        onRequest = { nextPage ->
            Log.e("Next Page :",nextPage.value.toString())
            delay(1000L)
            Result.success(api.getAllProducts(limit = 10, offset = nextPage.value).products)

        },
        getNextKey = {
            _page.value += 1
            _page
        },
        onError = {
            _isError.value = true
            _msg.value = it?.localizedMessage ?: "An error occurred"

        },
        onSuccess = { items, newKey ->
            addProducts(items = items)
            _page.value = newKey.value
            _endReached.value = items.isEmpty()
//            state = state.copy(
//                items = state.items + items,
//                page = newKey,
//                endReached = items.isEmpty()
//            )
        }
    )
    private fun addProducts(items: List<Product>) {
        val newList = ArrayList(_products.value)
        items.forEach {  item ->  newList.add(item) }
        _products.value = newList
    }


    fun loadNextItems() {
        viewModelScope.launch {
            paginator.loadNextItems()
        }
    }

    private fun getProducts(){
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response = api.getAllProducts(limit = 10, offset = 0)
                _isLoading.value = false
                if(!response.success){
                    _isError.value = true
                    _errorMsg.value = response.msg
                }
                response.products?.let {
                    _products.value = it
                }
            }catch (e: HttpException){
                Log.e("HTTP ERROR",e.localizedMessage ?: "Http error")
                _isLoading.value = false
                _isError.value = true
                _errorMsg.value = e.localizedMessage?: "An unexpected error occurred"

            }catch (e: IOException){
                Log.e("IO ERROR",e.toString() )
                _isLoading.value = false
                _isError.value = true
                _errorMsg.value = "Please check your internet connection"
            }
        }
    }
}