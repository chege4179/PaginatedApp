package com.peterchege.paginatedapp.ui.screens

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.paginatedapp.api.PaginatedApi
import com.peterchege.paginatedapp.models.Product
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

    private val _msg = mutableStateOf("")
    val msg: State<String> = _msg

    init {
        getProducts()
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