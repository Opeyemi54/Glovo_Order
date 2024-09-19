package com.example.glovoorder.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.RenderProcessGoneDetail
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.glovoorder.R
import com.example.glovoorder.databinding.MenuListRowBinding
import com.example.glovoorder.models.Menus
//import kotlinx.android.synthetic.main.menu_list_row.view.*

class MenuListAdapter(private val menuList: List<Menus?>?, val clickListener: MenuListClickListener): RecyclerView.Adapter<MenuListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuListAdapter.MyViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.menu_list_row, parent, false)
        return MyViewHolder(binding = MenuListRowBinding.bind(view))
    }

    override fun onBindViewHolder(holder: MenuListAdapter.MyViewHolder, position: Int) {
        holder.bind(menuList?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return menuList?.size ?: return 0
    }

    inner class MyViewHolder(private val binding: MenuListRowBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(menus: Menus) {
            binding.glovoOrder = menus
            //menuName.text = menus.name
            binding.menuPrice.text = "Price: $ ${menus.price}"
           binding.addToCartButton.setOnClickListener {
                menus.totalInCart = 1
                clickListener.addToCartClickListener(menus)
                binding.addMoreLayout.visibility = View.VISIBLE
                binding.addToCartButton.visibility = View.GONE
                binding.tvCount.text = menus.totalInCart.toString()
            }

            binding.imageMinus.setOnClickListener {
                var total: Int? = menus.totalInCart
                total = total!! - 1
                if(total > 0) {
                    menus.totalInCart = total
                    clickListener.updateCartClickListener(menus)
                   binding.tvCount.text = menus.totalInCart.toString()
                } else {
                    menus.totalInCart = total
                    clickListener.removeFromCartClickListener(menus)
                    binding.addMoreLayout.visibility = View.GONE
                    binding.addToCartButton.visibility = View.VISIBLE
                }
            }
            binding.imageAddOne.setOnClickListener {
                var total: Int? = menus.totalInCart
                total = total!! + 1
                if(total!! <= 10) {
                    menus.totalInCart = total as Int
                    clickListener.updateCartClickListener(menus)
                    binding.tvCount.text = total.toString()
                }
            }

//            Glide.with(thumbImage)
//                .load(menus.url)
//                .into(thumbImage)
        }
    }

    interface MenuListClickListener {
        fun addToCartClickListener(menu: Menus)
        fun updateCartClickListener(menu: Menus)
        fun removeFromCartClickListener(menu: Menus)
    }
}