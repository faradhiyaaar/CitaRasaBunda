package com.faradhy.citarasabunda.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.faradhy.citarasabunda.CitaRasaBundaApp
import com.faradhy.citarasabunda.R
import com.faradhy.citarasabunda.di.Injection
import com.faradhy.citarasabunda.model.FoodOrder
import com.faradhy.citarasabunda.ui.common.UiState
import com.faradhy.citarasabunda.ui.components.MenuItem
import androidx.compose.runtime.getValue
import com.faradhy.citarasabunda.ui.ViewModelFactory
import com.faradhy.citarasabunda.ui.theme.CitaRasaBundaTheme


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,
) {
    val query by viewModel.query
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        SearchBar(
            query = query,
            onQueryChange = viewModel::search,
            modifier = Modifier.fillMaxWidth()
        )
        viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getAllMenues()
                }

                is UiState.Success -> {
                    HomeContent(
                        foodOrders = uiState.data,
                        modifier = modifier,
                        navigateToDetail = navigateToDetail,
                    )
                }

                is UiState.Error -> {}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    query:String,
    onQueryChange: (String)-> Unit,
    modifier: Modifier =Modifier
){
    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = {},
        active = false,
        onActiveChange ={},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant)
        },
       placeholder ={
                    Text(stringResource(R.string.button_search))
       },
        shape = MaterialTheme.shapes.large,
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .heightIn(min = 48.dp)

    ) {

    }
}

@Composable
fun HomeContent(
    foodOrders: List<FoodOrder>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(160.dp),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(foodOrders) { data ->
            MenuItem(
                image = data.foodMenu.image,
                title = data.foodMenu.title,
                requiredPrice = data.foodMenu.price,
                modifier = Modifier.clickable {
                    navigateToDetail(data.foodMenu.id)
                }
            )
        }
    }
}

@Preview
@Composable
fun HomePreview(){
    CitaRasaBundaTheme {
        CitaRasaBundaApp()
    }
}