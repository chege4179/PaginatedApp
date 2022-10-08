package com.peterchege.paginatedapp.api


import com.peterchege.paginatedapp.models.AllProductsResponse
import com.peterchege.paginatedapp.util.Constants
import com.peterchege.paginatedapp.util.Constants.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface PaginatedApi {
    @GET("/products")
    suspend fun getAllProducts(
        @Query("limit") limit:Int,
        @Query("offset") offset:Int)
    :AllProductsResponse
    companion object {
        val instance by lazy {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(PaginatedApi::class.java)
        }
    }
}