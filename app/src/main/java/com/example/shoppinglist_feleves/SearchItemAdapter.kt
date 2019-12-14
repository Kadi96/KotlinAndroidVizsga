package com.example.shoppinglist_feleves

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SearchItemAdapter(itemTypesParam: Array<String>) : RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder>() {

    private var listener: IOnItemClickListener? = null
    private var itemTypes = itemTypesParam

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.search_rw_item, parent, false)
        return SearchItemViewHolder(itemView)
    }

    override fun getItemCount(): Int {
       return itemTypes.size
    }

    override fun onBindViewHolder(holder: SearchItemViewHolder, position: Int) {
        holder.typeText.text = itemTypes[position]
        when (holder.typeText.text) {
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

    fun setOnItemClickListener(listener: IOnItemClickListener) {
        this.listener = listener
    }

    inner class SearchItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var typeText: TextView = itemView.findViewById(R.id.rv_search_text)
        var productImage : ImageView = itemView.findViewById(R.id.circleImageView)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemTypeClick(itemTypes[position])
                }
            }
        }
    }
}