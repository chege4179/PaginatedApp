package com.peterchege.paginatedapp.models

data class Product(
    val attachments: Attachments,
    val category: String,
    val description: Any?,
    val discontinued: Int,
    val id: Int,
    val list_price: Double,
    val minimum_reorder_quantity: Int?,
    val product_code: String,
    val product_name: String,
    val quantity_per_unit: String?,
    val reorder_level: Int,
    val standard_cost: Double,
    val supplier_ids: String,
    val target_level: Int
)