package com.faradhy.citarasabunda.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.faradhy.citarasabunda.data.FoodRepository
import com.faradhy.citarasabunda.ui.screen.detail.DetailViewModel
import com.faradhy.citarasabunda.ui.screen.home.HomeViewModel
import com.faradhy.citarasabunda.ui.screen.keranjang.KeranjangViewModel

class ViewModelFactory(private val repository: FoodRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(KeranjangViewModel::class.java)) {
            return KeranjangViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}