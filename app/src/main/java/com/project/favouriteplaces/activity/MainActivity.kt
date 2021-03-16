package com.project.favouriteplaces.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.project.favouriteplaces.fragments.AddFavPlacesFragment
import com.project.favouriteplaces.fragments.MainFragment
import com.project.favouriteplaces.R


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
        }

        if (fragment != null) {
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.initialScreen, fragment, tag)

            ft.addToBackStack(null) // add fragment transaction to the back stack

            ft.commit()
        }
    }

}