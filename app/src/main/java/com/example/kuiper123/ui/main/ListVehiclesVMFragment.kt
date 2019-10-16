package com.example.kuiper123.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kuiper123.Adapter_View.VehiclesAdapter
import com.example.kuiper123.Model.Vehicles

import com.example.kuiper123.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 */
class ListVehiclesVMFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list_vehicles_vm,container,false)
        val user  = FirebaseAuth.getInstance().currentUser
        val vehiclesList = ArrayList<Vehicles>()

        database =  FirebaseDatabase.getInstance()
        dbReference =  database.reference.child("User").child(user?.uid.toString()).child("Vehicles")


        recyclerView = view.findViewById(R.id.recycler)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this.context,RecyclerView.VERTICAL,false)

        dbReference.addValueEventListener(object : ValueEventListener{

            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(dataSnapshot : DataSnapshot) {
                if (dataSnapshot.exists()){
                    for(dataSnap in dataSnapshot.children){
                        var userVM = dataSnap.getValue(Vehicles::class.java)
                        vehiclesList.add(userVM!!)
                    }

                    val adapter = VehiclesAdapter(vehiclesList)
                   // adapter.notifyDataSetChanged()
                    recyclerView.adapter = adapter


                   //val user = FirebaseAuth.getInstance().currentUser
                    /*
                    if (user!= null){

                        val model =  dataSnapshot.child(user?.uid.toString()).child("Vehicles").child("Model").getValue().toString()
                        val make =  dataSnapshot.child(user?.uid.toString()).child("Vehicles").child("Make").getValue().toString()
                        val year =  dataSnapshot.child(user?.uid.toString()).child("Vehicles").child("Year").getValue().toString()

                        Log.d("Values","1 : "+model)
                        Log.d("Values","2:"+make)
                        Log.d("Values","3  : "+year)

                        if (model!=null && make!=null && year!=null){



                        }


                    }

                    else{
                        Toast.makeText(context,"Datos Null",Toast.LENGTH_LONG).show()

                    }*/
                }
            }


        })

        return view
    }


}
