package com.example.glovoorder.adapter



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.glovoorder.R
import com.example.glovoorder.models.Hours
import com.example.glovoorder.models.RestaurantModel
import java.text.SimpleDateFormat
import java.util.*

class RestaurantListAdapter(val restaurantList: List<RestaurantModel?>?, val clickListener: RestaurantListClickListener): RecyclerView.Adapter<RestaurantListAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantListAdapter.MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recycler_restaurant_list_row, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantListAdapter.MyViewHolder, position: Int) {
        holder.bind(restaurantList?.get(position))
        holder.itemView.setOnClickListener {
            clickListener.onItemClick(restaurantList?.get(position)!!)
        }
    }

    override fun getItemCount(): Int {
        return restaurantList?.size!!
    }

    inner class MyViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        val thumbImage: ImageView = view.findViewById(R.id.thumbImage)
        val tvRestaurantName: TextView = view.findViewById(R.id.tvRestaurantName)
        val tvRestaurantAddress: TextView = view.findViewById(R.id.tvRestaurantAddress)
        val tvRestaurantHours: TextView = view.findViewById(R.id.tvRestaurantHours)

        fun bind(restaurentModel: RestaurantModel?) {
            tvRestaurantName.text = restaurentModel?.name
            tvRestaurantAddress.text = "Address: "+restaurentModel?.address
            tvRestaurantHours.text = "Today's Hours: " + getTodaysHours(restaurentModel?.hours!!)

            if (restaurentModel != null) {
                Glide.with(thumbImage)
                    .load(restaurentModel.image)
                    .into(thumbImage)
            }
        }
    }

    private fun getTodaysHours(hours: Hours): String? {
        val calendar: Calendar =  Calendar.getInstance()
        val date: Date = calendar.time
        val day : String = SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.time)
        return when(day) {
            "Sunday" -> hours.Sunday
            "Monday" -> hours.Monday
            "Tuesday" -> hours.Tuesday
            "Wednesday" -> hours.Wednesday
            "Thursday" -> hours.Thursday
            "Friday" -> hours.Friday
            "Saturday" -> hours.Saturday
            else -> hours.Sunday
        }
    }

    interface RestaurantListClickListener {
        fun onItemClick(restaurantModel: RestaurantModel)
    }

}