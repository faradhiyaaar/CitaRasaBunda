package com.faradhy.citarasabunda.ui.screen.keranjang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faradhy.citarasabunda.data.FoodRepository
import com.faradhy.citarasabunda.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class KeranjangViewModel (val foodRepository: FoodRepository):ViewModel() {
    private val _uiState: MutableStateFlow<UiState<KeranjangState>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<KeranjangState>>
        get() = _uiState

    fun getAddedOrderFood(){
        viewModelScope.launch {
            _uiState.value =UiState.Loading
            foodRepository.getAddedOrderFood()
                .collect{orderMenu->
                    val totalHarga = orderMenu.sumOf { it.foodMenu.price * it.count }
                    _uiState.value =UiState.Success(KeranjangState(orderMenu,totalHarga))
                }
        }
    }

    fun updateOrderMenu(menuId : Long, count: Int){
        viewModelScope.launch {
            foodRepository.updateOrderFood(menuId,count)
                .collect{isUpdate->
                    if (isUpdate){
                        getAddedOrderFood()
                    }
                }
        }
    }
}