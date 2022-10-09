package com.peterchege.paginatedapp.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.paging.compose.items

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.peterchege.paginatedapp.ui.ProductCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
) {
    val viewModel = viewModel<HomeScreenViewModel>()
    val productsData = viewModel.productsPager.collectAsLazyPagingItems()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {

            items(items = productsData) { product ->
                product?.let {
                    ProductCard(product = it)
                }
            }
            when (productsData.loadState.append) {
                is LoadState.NotLoading -> Unit
                LoadState.Loading -> {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
                is LoadState.Error -> {
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth(),

                        ) {
                            Text(text = (productsData.loadState.append as LoadState.Error).error.message.toString())

                        }
                    }
                }
            }

        }

    }
}