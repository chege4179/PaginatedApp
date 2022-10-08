package com.peterchege.paginatedapp.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.peterchege.paginatedapp.ui.ProductCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
) {
    val viewModel = viewModel<HomeScreenViewModel>()
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),

        ) {
        LaunchedEffect(key1 = viewModel.page.value) {
            Toast.makeText(context, "Current Page : ${viewModel.page.value}", Toast.LENGTH_LONG)
                .show()
        }
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(items = viewModel.products.value) { product ->
                val i = viewModel.products.value.indexOf(product)

                if (i <= viewModel.products.value.size - 1 && !viewModel.endReached.value && !viewModel.isLoading.value) {
                    viewModel.loadNextItems()
                }
                ProductCard(
                    product = product,
                    productIndex = product.id
                )
            }
            item {
                if (viewModel.isLoading.value) {
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

        }

    }
}