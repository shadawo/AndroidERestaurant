package fr.isen.ribe.androiderestaurant.models
import java.io.Serializable
data class DishBasket(val dishes: MutableList<BasketData>, var quantity: Int): Serializable

data class BasketData(val dishName: DishModel, var quantity : Int): Serializable

