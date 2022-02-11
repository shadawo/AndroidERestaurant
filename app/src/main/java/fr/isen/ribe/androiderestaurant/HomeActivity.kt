package fr.isen.ribe.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.ribe.androiderestaurant.databinding.ActivityHomeBinding

class HomeActivity : ToolActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.starters.setOnClickListener {
            changeActivity(getString(R.string.home_starters))
        }
        binding.dishes.setOnClickListener {
            changeActivity(getString(R.string.home_dish))
        }
        binding.desserts.setOnClickListener {
            changeActivity(getString(R.string.home_dessert))
        }
    }

    private fun changeActivity(category: String) {
        val changePage: Intent = Intent(this, DishesActivity::class.java)
        changePage.putExtra("category_type",category)
        Log.i("INFO","End of HomeActivity")
        startActivity(changePage)
    }


}