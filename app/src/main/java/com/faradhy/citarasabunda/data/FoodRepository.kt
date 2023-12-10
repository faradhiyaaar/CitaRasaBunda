package com.faradhy.citarasabunda.data

import com.faradhy.citarasabunda.model.FoodFakeDataSource
import com.faradhy.citarasabunda.model.FoodOrder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FoodRepository {

    private val foodOrders = mutableListOf<FoodOrder>()

    init {
        if(foodOrders.isEmpty()){
            FoodFakeDataSource.dummyFoodFoodMenus.forEach {
                foodOrders.add(FoodOrder(it,0))
            }
        }
    }

    fun getAllFood(): Flow<List<FoodOrder>>{
        return flowOf(foodOrders)
    }

    fun getOrderFoodById(menuId:Long):FoodOrder{
        return foodOrders.first{
            it.foodMenu.id == menuId
        }
    }

    fun updateOrderFood(menuId: Long, newCountValue:Int): Flow<Boolean>{
        val index = foodOrders.indexOfFirst { it.foodMenu.id == menuId }
        val result = if(index>=0){
            val orderMenus = foodOrders[index]
            foodOrders[index]=
                orderMenus.copy(orderMenus.foodMenu,newCountValue)
            true
        }else{
            false
        }
        return flowOf(result)
    }

    fun getAddedOrderFood(): Flow<List<FoodOrder>>{
        return getAllFood()
            .map { orderMenu->
                orderMenu.filter {orderMenues ->
                orderMenues.count!=0
                }
            }
    }

    fun searchFood(query: String):Flow<List<FoodOrder>>{
        return flowOf(foodOrders.filter {
            it.foodMenu.title.contains(query,ignoreCase = true)

        })
    }

    companion object{
        @Volatile
        private var instance:FoodRepository?=null

        fun getInstance():FoodRepository=
            instance?: synchronized(this){
                FoodRepository().apply {
                    instance=this
                }
            }
    }
}