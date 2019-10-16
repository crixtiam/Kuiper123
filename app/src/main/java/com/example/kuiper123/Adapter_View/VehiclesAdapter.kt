package com.example.kuiper123.Adapter_View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.kuiper123.Model.Vehicles
import com.example.kuiper123.R


class VehiclesAdapter(var list: ArrayList<Vehicles>):RecyclerView.Adapter<VehiclesAdapter.vehiclesAdapterViewHolder>() {

    private var context : Context? = null


    private var fullListVehicles : ArrayList<Vehicles> = ArrayList(list)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vehiclesAdapterViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.template_vehicles_adapter, parent, false)
        return vehiclesAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: vehiclesAdapterViewHolder, position: Int) {

        holder.loadReference(list[position])
    }


    class vehiclesAdapterViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){

        fun loadReference(vehicles : Vehicles){

            val model : TextView = itemView.findViewById(R.id.tModelVM)
            val make : TextView = itemView.findViewById(R.id.tMakeVM)
            val year1 : TextView =  itemView.findViewById(R.id.tYearVM)
            val licencePlate : TextView = itemView.findViewById(R.id.tLicencePltVM)
            val type : TextView = itemView.findViewById(R.id.tTypeVM)
            val VIN : TextView = itemView.findViewById(R.id.tVINVM)
            val color1: TextView = itemView.findViewById(R.id.tColorVM)

            model.text = vehicles.Model
            make.text = vehicles.Make
            year1.text = vehicles.Year
            licencePlate.text = vehicles.Licence_Plate
            type.text = vehicles.Type
            VIN.text = vehicles.VIN
            color1.text = vehicles.Color





            itemView.setOnClickListener{
                Toast.makeText(itemView.context,vehicles.Model,Toast.LENGTH_LONG).show()
            }


        }


    }
}
