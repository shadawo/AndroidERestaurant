package fr.isen.ribe.androiderestaurant

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.ribe.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.ribe.androiderestaurant.databinding.ActivityDishesBinding
import fr.isen.ribe.androiderestaurant.models.BasketData
import fr.isen.ribe.androiderestaurant.models.DishBasket
import fr.isen.ribe.androiderestaurant.models.DishModel

import java.io.File

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)
        val dish = intent.getSerializableExtra("dish") as DishModel
        initDetail(dish)

    }
    private fun initDetail(dish:DishModel){
        var nbInBucket = 1

        binding.dishDetailTitle.text=dish.name_fr
        binding.dishPhotoPager.adapter=DishPictureAdapter(this,dish.pictures)
        binding.dishIngredient.text=dish.ingredients.joinToString(", ") {it.name_fr}
        binding.priceButton.text=dish.getFormatedPrice()


        val minusButton=binding.minusButton
        val plusButton=binding.plusButton

        minusButton.setOnClickListener{
            if(nbInBucket>1) {
                nbInBucket--
                binding.nbrOfDish.text = nbInBucket.toString()
                binding.priceButton.text=(dish.prices[0].price.toFloat()*nbInBucket).toString() +"€"
            }
        }
        plusButton.setOnClickListener{
            nbInBucket++
            binding.nbrOfDish.text=nbInBucket.toString()
            binding.priceButton.text=(dish.prices[0].price.toFloat()*nbInBucket).toString() +"€"
        }
        val priceButton=binding.priceButton
        priceButton.setOnClickListener {
            var data = ArrayList<BasketData>()
            val filename = "/panier.json"
            if (File(cacheDir.absolutePath + filename).exists()) {
                var basketNumberOfElement: Int
                Snackbar.make(it, "Ajouté au panier", Snackbar.LENGTH_LONG).show()
                if (File(cacheDir.absolutePath + filename).readText().isNotEmpty()) {
                    val file = File(cacheDir.absolutePath + filename).readText();
                    val result = Gson().fromJson(file, DishBasket::class.java)
                    basketNumberOfElement = result.quantity
                    for (j in result.dishes.indices) {
                        BasketAdd(
                            BasketData(
                                result.dishes[j].dishName,
                                result.dishes[j].quantity
                            ), data
                        )
                    }

                    BasketAdd(BasketData(dish, nbInBucket), data)
                    basketNumberOfElement += nbInBucket
                    val editor = sharedPreferences.edit()
                    editor.putInt(basketCount, basketNumberOfElement)
                    editor.apply()
                    File(cacheDir.absolutePath + filename).writeText(
                        Gson().toJson(
                            DishBasket(
                                data,
                                basketNumberOfElement
                            )
                        )
                    )
                } else {
                    File(cacheDir.absolutePath + filename).writeText(
                        Gson().toJson(
                            DishBasket(
                                mutableListOf(BasketData(dish, nbInBucket)),
                                1
                            )
                        )
                    )
                    val editor = sharedPreferences.edit()
                    editor.putInt(basketCount, 1)
                    editor.apply()
                }
            }
            else{
                File(cacheDir.absolutePath + filename).writeText(
                    Gson().toJson(
                        DishBasket(
                            mutableListOf(BasketData(dish, nbInBucket)),
                            1
                        )
                    )
                )
                val editor = sharedPreferences.edit()
                editor.putInt(basketCount, 1)
                editor.apply()
            }
            startActivity(Intent(this,HomeActivity::class.java))
        }
    }

    private fun BasketAdd(
        objectToAdd: BasketData,
        data: ArrayList<BasketData>
    ) {
        var bool = false

        for (i in data.indices)
            if (objectToAdd.dishName == data[i].dishName) {
                data[i].quantity += objectToAdd.quantity
                bool = true
            }
        if (!bool) data.add(
            BasketData(
                objectToAdd.dishName,
                objectToAdd.quantity
            )
        )

    }
    companion object {
        const val APP_PREFS = "app_prefs"
        const val basketCount = "basket_count"
    }

    }
