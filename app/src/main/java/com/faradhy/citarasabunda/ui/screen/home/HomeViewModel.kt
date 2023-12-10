package com.faradhy.citarasabunda.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradhy.citarasabunda.data.FoodRepository
import com.faradhy.citarasabunda.model.FoodOrder
import com.faradhy.citarasabunda.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: FoodRepository
):ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<FoodOrder>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<FoodOrder>>>
        get() = _uiState

    fun getAllMenues() {
        viewModelScope.launch {
            repository.getAllFood()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { orderRewards ->
                    _uiState.value = UiState.Success(orderRewards)
                }
        }
    }

    private  val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        viewModelScope.launch {
            try {
                val result = repository.searchFood(_query.value)
                    .map { data -> data.sortedBy { it.foodMenu.title } }
                    .first()

                _uiState.value = UiState.Success(result)
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message.toString())
            }
        }
    }
}