package com.example.glovoorder.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.glovoorder.adapter.PlaceYourOrderAdapter
import com.example.glovoorder.databinding.ActivityPlaceYourOrderBinding
import com.example.glovoorder.models.RestaurantModel


class PlaceYourOrderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlaceYourOrderBinding

    private var placeYourOrderAdapter: PlaceYourOrderAdapter? = null
    private var isDeliveryOn: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaceYourOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val restaurantModel: RestaurantModel? = intent.getParcelableExtra("RestaurantModel")
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = restaurantModel?.name
        actionbar?.subtitle = restaurantModel?.address
        actionbar?.setDisplayHomeAsUpEnabled(true)

       binding.buttonPlaceYourOrder.setOnClickListener {
            onPlaceOrderButtonCLick(restaurantModel)
        }

        binding.switchDelivery.setOnCheckedChangeListener { _, isChecked ->

            if(isChecked) {
                binding.apply {
                    inputAddress.visibility = View.VISIBLE
                    inputCity.visibility = View.VISIBLE
                    inputState.visibility = View.VISIBLE
                    inputZip.visibility = View.VISIBLE
                    tvDeliveryCharge.visibility = View.VISIBLE
                    tvDeliveryChargeAmount.visibility = View.VISIBLE
                }
                isDeliveryOn = true
                calculateTotalAmount(restaurantModel)
            } else {
                binding.apply {
                    inputAddress.visibility = View.GONE
                    inputCity.visibility = View.GONE
                    inputState.visibility = View.GONE
                    inputZip.visibility = View.GONE
                    tvDeliveryCharge.visibility = View.GONE
                    tvDeliveryChargeAmount.visibility = View.GONE
                }
                isDeliveryOn = false
                calculateTotalAmount(restaurantModel)
            }
        }

        initRecyclerView(restaurantModel)
        calculateTotalAmount(restaurantModel)
    }

    private fun initRecyclerView(restaurantModel: RestaurantModel?) {
        binding.cartItemsRecyclerView.layoutManager = LinearLayoutManager(this)
        placeYourOrderAdapter = PlaceYourOrderAdapter(restaurantModel?.menus)
        binding.cartItemsRecyclerView.adapter =placeYourOrderAdapter
    }

    private fun calculateTotalAmount(restaurantModel: RestaurantModel?) {
        var subTotalAmount = 0f
        for(menu in restaurantModel?.menus!!) {
            subTotalAmount += menu?.price!!  * menu.totalInCart

        }
        binding.tvSubtotalAmount.text = "$"+ String.format("%.2f", subTotalAmount)
        if(isDeliveryOn) {
           binding.tvDeliveryChargeAmount.text = "$"+String.format("%.2f", restaurantModel.delivery_charge?.toFloat())
            subTotalAmount += restaurantModel.delivery_charge?.toFloat()!!
        }

        binding.tvTotalAmount.text = "$"+ String.format("%.2f", subTotalAmount)
    }

    private fun onPlaceOrderButtonCLick(restaurantModel: RestaurantModel?) {
        binding.apply {
            if(TextUtils.isEmpty(inputName.text.toString())) {
                inputName.error =  "Enter your name"
                return
            } else if(isDeliveryOn && TextUtils.isEmpty(inputAddress.text.toString())) {
                inputAddress.error =  "Enter your address"
                return
            } else if(isDeliveryOn && TextUtils.isEmpty(inputCity.text.toString())) {
                inputCity.error =  "Enter your City Name"
                return
            } else if(isDeliveryOn && TextUtils.isEmpty(inputZip.text.toString())) {
                inputZip.error =  "Enter your Zip code"
                return
            } else if( TextUtils.isEmpty(inputCardNumber.text.toString())) {
                inputCardNumber.error =  "Enter your credit card number"
                return
            } else if( TextUtils.isEmpty(inputCardExpiry.text.toString())) {
                inputCardExpiry.error =  "Enter your credit card expiry"
                return
            } else if( TextUtils.isEmpty(inputCardPin.text.toString())) {
                inputCardPin.error =  "Enter your credit card pin/cvv"
                return
            }
        }

        val intent = Intent(this@PlaceYourOrderActivity, SuccessOrder::class.java)
        intent.putExtra("RestaurantModel", restaurantModel)
        startActivityForResult(intent, 1000)
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == 1000) {
            setResult(RESULT_OK)
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }

    @Deprecated("This method has been deprecated in favor of using the\n      {@link OnBackPressedDispatcher} via {@link #getOnBackPressedDispatcher()}.\n      The OnBackPressedDispatcher controls how back button events are dispatched\n      to one or more {@link OnBackPressedCallback} objects.")
    override fun onBackPressed() {
        super.onBackPressed()
        setResult(RESULT_CANCELED)
        finish()
    }
}