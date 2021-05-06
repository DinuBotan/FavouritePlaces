package com.project.favouriteplaces.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.database.FavPlace
import kotlinx.android.synthetic.main.fragment_fav_place_detail.view.*


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

        var bundle = this.arguments

       // if(bundle != null){
            var receivedModel = bundle?.getSerializable("placeInfo") as FavPlace
            rootView.iv_place_image_detail.setImageURI(Uri.parse(receivedModel.placeImage))
            rootView.tv_description.text = receivedModel.placeDescription
            rootView.tv_location.text = receivedModel.placeLocation

       // }

        rootView.btn_view_on_map.setOnClickListener{
            var bundle = Bundle()
            bundle.putSerializable("placeInfo", receivedModel)
            var fr = MapFragment()
            fr.arguments = bundle

            (activity as MainActivity).showFragment(fr)
        }



        return rootView
    }

}