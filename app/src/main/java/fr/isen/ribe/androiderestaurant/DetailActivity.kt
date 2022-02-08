package fr.isen.ribe.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.ribe.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.ribe.androiderestaurant.databinding.ActivityDishesBinding
import fr.isen.ribe.androiderestaurant.models.DishModel

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dish = intent.getSerializableExtra("dish") as DishModel
        initDetail(dish)

    }
    private fun initDetail(dish:DishModel){
        var nbInBucket = 1

        binding.dishDetailTitle.text=dish.name_fr
        binding.dishPhotoPager.adapter=DishPictureAdapter(this,dish.pictures)
        binding.dishIngredient.text=dish.ingredients.joinToString(", ") {it.name_fr}


    }
}