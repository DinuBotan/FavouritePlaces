package com.project.favouriteplaces.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.favouriteplaces.fragments.AddFavPlacesFragment
import com.project.favouriteplaces.fragments.MainFragment
import com.project.favouriteplaces.R
import com.project.favouriteplaces.adapters.FavPlacesAdapter
import com.project.favouriteplaces.database.AppDatabase
import com.project.favouriteplaces.database.FavPlace
import com.project.favouriteplaces.fragments.FavPlaceDetail
import kotlinx.android.synthetic.main.fragment_main2.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val addPlacesFragment =
            AddFavPlacesFragment()
        val mainFragment = MainFragment()

//        Set the initial fragment to our fragments container.
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.initialScreen, mainFragment)
            addToBackStack(null)
            commit()
        }

//        Thread{
//
//            var getFavPlaceList : ArrayList<FavPlace> = AppDatabase.getInstance(this).placeDao().getPlaces().toCollection(ArrayList())
//
//
//            runOnUiThread {
//                if (getFavPlaceList.size > 0) {
//                    setupFavPlacesRecyclerView(getFavPlaceList)
//
//                    rv_fav_places_list.visibility = View.VISIBLE
//                    tv_no_records_available.visibility = View.GONE
//                } else {
//                    rv_fav_places_list.visibility = View.GONE
//                    tv_no_records_available.visibility = View.VISIBLE
//                }
//            }
//        }.start()


    }

    public fun showFragmentByTag(tag: String) {
        var fragment = supportFragmentManager.findFragmentByTag(tag)
        if (fragment == null) {
            if (MainFragment.TAG == tag) {
                fragment = MainFragment()
            } else if (AddFavPlacesFragment.TAG == tag) {
                fragment =
                    AddFavPlacesFragment()
            }
            else if(FavPlaceDetail.TAG == tag){
                fragment = FavPlaceDetail()
            }
        }

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.initialScreen, fragment, tag)

            ft.addToBackStack(null) // add fragment transaction to the back stack

            ft.commit()
        }
    }

    public fun showFragmentByTag(fragment: Fragment){
        if(fragment!= null){
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.initialScreen, fragment, "TAG_FRAGMENT_DETAILS")
            ft.addToBackStack(null)
            ft.commit()
        }
    }

//    private fun setupFavPlacesRecyclerView(favPlaceList: ArrayList<FavPlace>){
//        rv_fav_places_list.layoutManager = LinearLayoutManager(this)
//        rv_fav_places_list.setHasFixedSize(true)
//
//        val placesAdapter = FavPlacesAdapter(this, favPlaceList)
//        rv_fav_places_list.adapter = placesAdapter
//
//        placesAdapter.setOnClickListener(object : FavPlacesAdapter.OnClickListener{
//            override fun onClick(position: Int, model: FavPlace) {
//                val intent = Intent(this@MainActivity, FavPlacesD)
//            }
//        })
//    }



}