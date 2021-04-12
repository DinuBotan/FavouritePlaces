package com.project.favouriteplaces.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.database.FavPlace
import kotlinx.android.synthetic.main.fragment_add_fav_places.view.*
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

        //var favPlaceDetailModel: FavPlace? = null
        //if((activity as MainActivity).intent.hasExtra())

        return rootView
    }

}