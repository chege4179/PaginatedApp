package com.peterchege.paginatedapp.repositoryImpl

import com.peterchege.paginatedapp.api.PaginatedApi
import com.peterchege.paginatedapp.models.AllProductsResponse
import com.peterchege.paginatedapp.repository.ProductsRepository

class ProductRepositoryImpl(
    private val api: PaginatedApi
) : ProductsRepository {

    override suspend fun getProducts(limit:Int,page:Int): AllProductsResponse {
        return api.getAllProducts( limit = limit, offset = page )
    }


}