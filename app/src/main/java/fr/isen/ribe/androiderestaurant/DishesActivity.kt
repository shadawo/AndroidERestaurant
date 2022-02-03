package fr.isen.ribe.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.LinearLayout
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import fr.isen.ribe.androiderestaurant.databinding.ActivityDishesBinding
import fr.isen.ribe.androiderestaurant.databinding.ActivityHomeBinding

class DishesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDishesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDishesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.mainDishTitle.text=intent.getStringExtra("category_type")

        binding.dishRecycleView.layoutManager= LinearLayoutManager(this)
        val data= ArrayList<String>()

        for(i in 1..20){
            data.add("Item"+i)
        }
        val adapter = DishAdapter(data)
        binding.dishRecycleView.adapter=adapter
    }
}