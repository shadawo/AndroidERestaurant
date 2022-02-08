package fr.isen.ribe.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.ribe.androiderestaurant.databinding.ActivityDishesBinding
import fr.isen.ribe.androiderestaurant.databinding.ActivityHomeBinding
import fr.isen.ribe.androiderestaurant.models.DishModel
import fr.isen.ribe.androiderestaurant.models.DishResult
import org.json.JSONObject

class DishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val dishCategory= intent.getStringExtra("category_type")
        binding.mainDishTitle.text =dishCategory


        if (dishCategory != null) {
            loadDishesFromCategory(dishCategory)
        }
    }
    private fun loadDishesFromCategory(category: String){
        val url = "http://test.api.catering.bluecodegames.com/menu"

        val jsonObject = JSONObject()
        jsonObject.put("id_shop", "1")
        val jsonRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject, { response ->
                val dishResult = Gson().fromJson(response.toString(), DishResult::class.java)
                displayDishes(dishResult.data.firstOrNull() { it.name_fr == category }?.items ?: listOf())
            }, {
                Log.e("DishActivity", "erreur lors de la récupération des plats")
            }
        )
        Volley.newRequestQueue(this).add(jsonRequest)
    }
    private fun displayDishes(dishes: List<DishModel>) {
        binding.dishRecycleView.layoutManager = LinearLayoutManager(this)

        val adapter = DishAdapter(dishes) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("dish", it)
            startActivity(intent)
        }
        binding.dishRecycleView.adapter = adapter
    }
}