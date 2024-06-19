package com.example.rempahpedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListSpicesAdapter(private val listSpices: List<Spices>) :
    RecyclerView.Adapter<ListSpicesAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_spice, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val spice = listSpices[position]
        holder.tvName.text = spice.name
        holder.tvLatinName.text = spice.cientificName
        Glide.with(holder.itemView.context)
            .load(spice.imageUrl1)
            .into(holder.imgPhoto)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listSpices[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int = listSpices.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_item_photo)
        val tvName: TextView = itemView.findViewById(R.id.tv_item_name)
        val tvLatinName: TextView = itemView.findViewById(R.id.tv_item_latin_name)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Spices)
    }
}
