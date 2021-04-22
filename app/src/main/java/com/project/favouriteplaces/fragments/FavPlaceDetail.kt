package com.project.favouriteplaces.fragments

import android.content.Intent
import android.content.res.loader.ResourcesLoader
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.database.FavPlace
import kotlinx.android.synthetic.main.fragment_add_fav_places.view.*
import kotlinx.android.synthetic.main.fragment_fav_place_detail.*
import kotlinx.android.synthetic.main.fragment_fav_place_detail.view.*
import kotlinx.android.synthetic.main.item_favourite_place.*
import kotlinx.android.synthetic.main.item_favourite_place.iv_place_image
import java.util.*


class FavPlaceDetail : Fragment() {

    companion object{
        const val TAG = "TAG_FRAGMENT_DETAILS"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_fav_place_detail, container, false)

        (activity as MainActivity).setSupportActionBar(rootView.toolbar_fav_place_detail)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rootView.toolbar_fav_place_detail.setNavigationOnClickListener{
            (activity as MainActivity).onBackPressed()
        }

       // var bundle = Bundle()
        var bundle = this.arguments
        Log.d("Deserialized", "Arguments received")
        if(bundle != null){
            var receivedModel = bundle.getSerializable("placeInfo") as FavPlace
            Log.d("Deserilized", receivedModel.placeDescription)
            iv_place_image.setImageURI(Uri.parse(receivedModel.placeImage))
//            tv_description?.text = receivedModel.placeDescription
//            tv_description?.text = "receivedModel.placeDescription"
            tv_location?.text = receivedModel.placeLocation
            testTV?.setText(receivedModel.placeDescription)


        }


        //var favPlaceDetailModel: FavPlace? = null
        //if((activity as MainActivity).intent.hasExtra())

        return rootView
    }

}