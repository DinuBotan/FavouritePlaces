package com.project.favouriteplaces.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.SupportMapFragment
import com.project.favouriteplaces.R
import com.project.favouriteplaces.adapters.FavPlacesAdapter
import com.project.favouriteplaces.database.AppDatabase
import com.project.favouriteplaces.database.FavPlace
import com.project.favouriteplaces.fragments.*
import kotlinx.android.synthetic.main.fragment_main2.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val introFragment = IntroFragment()

//        Set the initial fragment to our fragments container.
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.initialScreen, introFragment)
            addToBackStack(null)
            commit()
        }




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
            else if(SignUpFragment.TAG == tag){
                fragment = SignUpFragment()
            }
            else if(SignInFragment.TAG == tag){
                fragment = SignInFragment()
            }
        }

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.initialScreen, fragment, tag)

            ft.addToBackStack(null) // add fragment transaction to the back stack

            ft.commit()
        }
    }

    public fun showFragment(fragment: Fragment){
        if(fragment!= null){
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.initialScreen, fragment, "TAG_FRAGMENT_DETAILS")
            Log.d("Deserialized fragment", fragment.tag!!)
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