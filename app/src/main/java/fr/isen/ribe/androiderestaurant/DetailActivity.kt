package fr.isen.ribe.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.ribe.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.ribe.androiderestaurant.databinding.ActivityDishesBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.dishDetail.text=intent.getStringExtra("dish")

    }
}