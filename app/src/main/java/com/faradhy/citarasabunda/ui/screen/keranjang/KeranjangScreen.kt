package com.faradhy.citarasabunda.ui.screen.keranjang

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faradhy.citarasabunda.ui.ViewModelFactory
import com.faradhy.citarasabunda.di.Injection
import com.faradhy.citarasabunda.ui.common.UiState
import com.faradhy.citarasabunda.ui.components.OrderButton
import com.faradhy.citarasabunda.R
import com.faradhy.citarasabunda.ui.components.CartItem


@Composable
fun KeranjangScreen(
    viewModel: KeranjangViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),onOrderButtonClicked: (String) -> Unit
){
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedOrderFood()
            }
            is UiState.Success -> {
                KeranjangContent(
                    uiState.data,
                    onProductCountChanged = { menuId, count ->
                        viewModel.updateOrderMenu(menuId, count)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KeranjangContent(
    state: KeranjangState,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.foodOrders.count(),
        state.totalPrice
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.menu_cart),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center
                )
            }
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.weight(weight = 1f)
        ) {
            items(state.foodOrders, key = { it.foodMenu.id }) { item ->
                CartItem(
                    FoodId = item.foodMenu.id,
                    image = item.foodMenu.image,
                    title = item.foodMenu.title,
                    totalPrice = item.foodMenu.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                )
                Divider()
            }
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.foodOrders.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}