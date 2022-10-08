package com.peterchege.paginatedapp.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.peterchege.paginatedapp.ui.ProductCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun  HomeScreen(
){
    val viewModel = viewModel<HomeScreenViewModel>()
    Scaffold(
        modifier = Modifier.fillMaxSize(),

    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(items = viewModel.products.value){ product ->
               ProductCard(product = product)
            }

        }

    }
}