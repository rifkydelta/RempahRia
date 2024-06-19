package com.example.rempahpedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ListHomeAdapter(private val listSpices: List<Spices>) :
    RecyclerView.Adapter<ListHomeAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card_spice, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListHomeAdapter.ListViewHolder, position: Int) {
        val spice = listSpices[position]
        holder.textViewName.text = spice.name
        holder.textViewLatin.text = spice.cientificName
        Glide.with(holder.itemView.context)
            .load(spice.imageUrl1)
            .into(holder.imageView)
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listSpices[holder.adapterPosition]) }
    }


    override fun getItemCount(): Int = listSpices.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textViewName: TextView = itemView.findViewById(R.id.textViewName)
        val textViewLatin: TextView = itemView.findViewById(R.id.textViewLatin)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Spices)
    }
}