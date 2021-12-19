package com.example.swift.frontEnd.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.swift.businessLayer.businessLogic.RideRequest
import com.example.swift.frontEnd.adaptors.rideRequestListAdaptor
import androidx.recyclerview.widget.LinearLayoutManager


import androidx.recyclerview.widget.RecyclerView
import com.example.swift.R


private lateinit var rideRequestList : ArrayList<RideRequest>
private  lateinit var recyclerView: RecyclerView
class DriverRequestListFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_driver_request_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.Driver_RiderRequest_RecyclerView)
        load_data()
        init_recycler_view()
    }


    private fun load_data(){
        rideRequestList = ArrayList<RideRequest>()
        for (i in 1..10) {
            var user = RideRequest(null,null,"Arham Dilshad", "5.0",
                "Daewoo Express Terminal","FAST-NUCES Lahore",null)

            rideRequestList.add(user)
        }

    }

    private fun init_recycler_view(){
        var adapter = rideRequestListAdaptor(rideRequestList)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(view?.context)
    }
}