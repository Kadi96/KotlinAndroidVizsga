package com.example.shoppinglist_feleves


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class ProductAdapter : ListAdapter<Product, ProductAdapter.ProductHolder>(DIFF_CALLBACK) {

    private var listener: IOnItemClickListener? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.name == newItem.name && oldItem.comment == newItem.comment &&
                        oldItem.itemType == newItem.itemType
            }
        }
    }

    fun getProductAt(position: Int): Product {
        return getItem(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        return ProductHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductHolder, position: Int) {
        val current: Product = getItem(position)
        holder.productName.text = current.name
        holder.productComment.text = current.comment
        when (current.itemType) {
            "Tejtermék" -> { holder.productImage.setImageResource(R.drawable.dairy) }
            "Pékárú" -> { holder.productImage.setImageResource(R.drawable.bakery) }
            "Hús" -> { holder.productImage.setImageResource(R.drawable.meat) }
            "Gyümölcs-Zöldség" -> { holder.productImage.setImageResource(R.drawable.vegetables) }
            "Italok" -> { holder.productImage.setImageResource(R.drawable.drinks) }
            "Ruházat" -> { holder.productImage.setImageResource(R.drawable.clothes) }
            "Fagyaszott" -> { holder.productImage.setImageResource(R.drawable.frozen) }
            "Tartós élelmiszer" -> { holder.productImage.setImageResource(R.drawable.sustainable) }
            "Édesség" -> { holder.productImage.setImageResource(R.drawable.sweets) }
            "Egyéb" -> { holder.productImage.setImageResource(R.drawable.ic_shopping_cart_black_24dp) }
            else -> { holder.productImage.setImageResource(R.mipmap.ic_launcher) }
        }

    }

//    interface OnItemClickListener {
//        fun onItemClick(products: Product)
//        fun onDeleteClick(products: Product)
//    }

    fun setOnItemClickListener(listener: IOnItemClickListener) {
        this.listener = listener
    }

    inner class ProductHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var productName: TextView = itemView.rv_product_name
        var productComment: TextView = itemView.rv_product_comment
        var cleareImage: ImageView = itemView.item_delete_image
        var productImage: ImageView = itemView.circleImageView

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(getItem(position))
                }
            }
            cleareImage.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onDeleteClick(getItem(position))
                }
            }
        }
    }
}