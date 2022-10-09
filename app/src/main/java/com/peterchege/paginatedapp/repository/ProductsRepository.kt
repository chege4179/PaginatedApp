package com.peterchege.paginatedapp.repository

import com.peterchege.paginatedapp.models.AllProductsResponse

interface ProductsRepository {
    suspend fun getProducts( limit:Int,page:Int ) :AllProductsResponse
}