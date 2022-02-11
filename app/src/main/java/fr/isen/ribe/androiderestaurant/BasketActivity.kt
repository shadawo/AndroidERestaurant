package fr.isen.ribe.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import fr.isen.ribe.androiderestaurant.LoginFragment.Companion.USER_ID
import fr.isen.ribe.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.ribe.androiderestaurant.models.BasketData
import fr.isen.ribe.androiderestaurant.models.DishBasket
import java.io.File




class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasketBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE)
        binding.basketTitle.text = "Votre Panier"
        verifIfConnect()

        val filename = "/panier.json"
        if (File(cacheDir.absolutePath + filename).exists()) {
            val file = File(cacheDir.absolutePath + filename).readText()
            val result = Gson().fromJson(file, DishBasket::class.java)
            Log.d("panier", file)
            val data = ArrayList<BasketData>()
            for (j in result.dishes.indices) {
                data.add(BasketData(result.dishes[j].dishName, result.dishes[j].quantity))
            }

            displayDishes(DishBasket(data, result.quantity))
        }
        var buttonConnection = binding.buttonConnection
        buttonConnection.setOnClickListener {
            if (binding.buttonConnection.text == "Commander") {
                startActivity(Intent(this, OrderActivity::class.java))
            } else {
                startActivity(Intent(this, ConnectionActivity::class.java))
            }
        }
    }


    private fun displayDishes(dishResult: DishBasket) {
        binding.basketItem.layoutManager = LinearLayoutManager(this)
        binding.basketItem.adapter = BasketAdapter(dishResult.dishes) {
            dishResult.dishes.remove(it)
            updateBasket(dishResult)
            invalidateOptionsMenu()
        }


    }

    private fun updateBasket(basket: DishBasket) {
        val filename = "/panier.json"
        dishCountInPref(basket)
        File(cacheDir.absolutePath + filename).writeText(
            GsonBuilder().create().toJson(basket)
        )
    }

    private fun dishCountInPref(basket: DishBasket) {
        val count = basket.dishes.sumOf { it.quantity }
        basket.quantity = count
        val editor = getSharedPreferences(DetailActivity.APP_PREFS, Context.MODE_PRIVATE).edit()
        editor.putInt(DetailActivity.basketCount, count)
        editor.apply()

    }

    private fun verifIfConnect() {
        val userIdSave =
            getSharedPreferences(DetailActivity.APP_PREFS, MODE_PRIVATE).contains(USER_ID)
        if (userIdSave) {
            binding.buttonConnection.text = "Commander"
        }
    }
}