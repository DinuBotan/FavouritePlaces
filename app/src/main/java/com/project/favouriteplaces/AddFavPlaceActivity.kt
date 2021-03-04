package com.project.favouriteplaces

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add_fav_place.*

class AddFavPlaceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fav_place)
        setSupportActionBar(toolbar_add_place)
        //Adding back button on toolbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //Goes back when back button pressed
        toolbar_add_place.setNavigationOnClickListener{
            onBackPressed()
        }
    }
}