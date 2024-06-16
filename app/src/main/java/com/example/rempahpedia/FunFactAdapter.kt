package com.example.rempahpedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.List

class FunFactAdapter(private val funFactList: List<String>) :
    RecyclerView.Adapter<FunFactAdapter.FunFactViewHolder>() {

    class FunFactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val funFactText: TextView = itemView.findViewById(R.id.textView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunFactViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fun_fact, parent, false)
        return FunFactViewHolder(view)
    }

    override fun onBindViewHolder(holder: FunFactViewHolder, position: Int) {
        holder.funFactText.text = funFactList[position]
    }

    override fun getItemCount(): Int {
        return funFactList.size
    }
}
