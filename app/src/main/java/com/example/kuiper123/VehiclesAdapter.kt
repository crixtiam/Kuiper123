package com.example.kuiper123

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class VehiclesAdapter:RecyclerView.Adapter<VehiclesAdapter.vehiclesAdapterViewHolder> {

    private var context : Context? = null

    constructor(){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vehiclesAdapterViewHolder {
        val view =
            LayoutInflater.from(context).inflate(R.layout.template_vehicles_adapter, parent, false)


        return vehiclesAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: vehiclesAdapterViewHolder, position: Int) {

        holder.loadReference()
    }


    class vehiclesAdapterViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){

        fun loadReference(){


        }


    }
}