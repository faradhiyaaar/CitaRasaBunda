package com.faradhy.citarasabunda.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradhy.citarasabunda.data.FoodRepository
import com.faradhy.citarasabunda.model.FoodMenu
import com.faradhy.citarasabunda.model.FoodOrder
import com.faradhy.citarasabunda.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    private val repository: FoodRepository
): ViewModel() {
    private val _uiState: MutableStateFlow<UiState<FoodOrder>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<FoodOrder>>
        get() = _uiState

    fun getMenuById(menuId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderFoodById(menuId))
        }
    }

    fun addToCart(foodMenu: FoodMenu, count: Int) {
        viewModelScope.launch {
            repository.updateOrderFood(foodMenu.id, count)
        }
    }
}