package fr.isen.ribe.androiderestaurant

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.ribe.androiderestaurant.models.DishModel

class DishAdapter(private val dishes: List<DishModel>,val onDishClicked:(DishModel)->Unit) : RecyclerView.Adapter<DishAdapter.DishViewHolder>(){

    class DishViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val dishNameView: TextView = view.findViewById(R.id.dishNameView)
        val dishPriceView: TextView=view.findViewById(R.id.dishPriceView)
        val dishPicView: ImageView=view.findViewById(R.id.dishPicView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DishViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_dish_layout, parent, false)

        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder, position: Int) {
        holder.dishNameView.text = dishes[position].name_fr

        Picasso.get()
            .load(dishes[position].getFirstPicture())
            .error(R.drawable.ic_launcher_foreground)
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.dishPicView)


        holder.dishPriceView.text=dishes[position].getFormatedPrice()

        holder.itemView.setOnClickListener{
            onDishClicked(dishes[position])
        }
    }

    override fun getItemCount(): Int {
        return dishes.size
    }

}