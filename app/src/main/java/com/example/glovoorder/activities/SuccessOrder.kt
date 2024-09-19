package com.example.glovoorder.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.glovoorder.databinding.ActivitySuccessOrderBinding
import com.example.glovoorder.models.RestaurantModel

class SuccessOrder : AppCompatActivity() {
    private lateinit var binding: ActivitySuccessOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantModel: RestaurantModel? = intent.getParcelableExtra("RestaurantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = restaurantModel?.name
        actionbar?.subtitle = restaurantModel?.address
        actionbar?.setDisplayHomeAsUpEnabled(false)

        binding.buttonDone.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }
}