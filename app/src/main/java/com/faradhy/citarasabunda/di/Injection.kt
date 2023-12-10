package com.faradhy.citarasabunda.di

import com.faradhy.citarasabunda.data.FoodRepository

object Injection {
    fun provideRepository(): FoodRepository {
        return FoodRepository.getInstance()
    }
}