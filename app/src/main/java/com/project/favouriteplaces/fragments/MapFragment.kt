package com.project.favouriteplaces.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.android.gms.maps.SupportMapFragment
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.database.FavPlace
import kotlinx.android.synthetic.main.fragment_fav_place_detail.view.*
import kotlinx.android.synthetic.main.fragment_map.view.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment(), OnMapReadyCallback{
    companion object{
        const val TAG = "TAG_FRAGMENT_MAP"
    }

    private var mFavPlaceDetails: FavPlace? = null
    private lateinit var mMap : GoogleMap
    private var mapReady = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_map, container, false)

//        //Code from youtube video
//        val map = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
//        map.getMapAsync {
//            googleMap -> mMap = googleMap
//            mapReady = true
//
//        }



        var bundle = this.arguments

         if(bundle != null){
           mFavPlaceDetails = bundle.getSerializable("placeInfo") as FavPlace
         }

        if(mFavPlaceDetails != null){
            (activity as MainActivity).setSupportActionBar(rootView.toolbar_map)
            (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
            rootView.toolbar_map.setNavigationOnClickListener{
                (activity as MainActivity).onBackPressed()
            }
        }

        val supportMapFragment: SupportMapFragment =
//            (activity as MainActivity).supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportMapFragment.getMapAsync(this)

        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        Log.d("map1 ", "Map is ready")
        val pos = LatLng(mFavPlaceDetails!!.placeLatitude, mFavPlaceDetails!!.placeLongitude)
        googleMap!!.addMarker(MarkerOptions().position(pos).title(mFavPlaceDetails!!.placeLocation))
        val newLatLngZoom = CameraUpdateFactory.newLatLngZoom(pos, 15f)
        googleMap.animateCamera(newLatLngZoom)
    }

}