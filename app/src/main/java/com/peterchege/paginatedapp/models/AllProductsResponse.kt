package com.peterchege.paginatedapp.models

data class AllProductsResponse(
    val msg: String,
    val products: List<Product>,
    val productsCount: Int,
    val success: Boolean
)