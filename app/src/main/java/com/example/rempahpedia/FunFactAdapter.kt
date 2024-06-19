package com.example.rempahpedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FunFactAdapter(private val funFactList: List<Spices>) :
    RecyclerView.Adapter<FunFactAdapter.FunFactViewHolder>() {
    private lateinit var onItemClickCallback: ListHomeAdapter.OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: ListHomeAdapter.OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class FunFactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val funFactName: TextView = itemView.findViewById(R.id.fun_name)
        val funFactText: TextView = itemView.findViewById(R.id.fun_content)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunFactViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_fun_fact, parent, false)
        return FunFactViewHolder(view)
    }

    override fun onBindViewHolder(holder: FunFactViewHolder, position: Int) {
        val funFact = funFactList[position]
        holder.funFactName.text = funFact.name
        holder.funFactText.text = funFact.uniqueFact
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(funFactList[holder.adapterPosition]) }
    }

    override fun getItemCount(): Int {
        return funFactList.size
    }

}
