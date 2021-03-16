package com.project.favouriteplaces.fragments
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.database.AppDatabase
import com.project.favouriteplaces.database.Place
import kotlinx.android.synthetic.main.fragment_add_fav_places.*
import kotlinx.android.synthetic.main.fragment_add_fav_places.view.*
import java.lang.Appendable

class AddFavPlacesFragment : Fragment() {

    companion object{
        const val TAG = "TAG_FRAGMENT_ADD_PLACES"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_fav_places, container, false)
//       (activity as MainActivity).setSupportActionBar(toolbar_add_place)
//        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        toolbar_add_place.setNavigationOnClickListener{
//            (activity as MainActivity).onBackPressed()
//        }

        rootView.btn_save.setOnClickListener{
            var newPlace = Place(null, et_title.text.toString(), til_description.toString(), et_location.toString())
            savePlace(newPlace)
        }


        return rootView;
    }

    private fun savePlace(place: Place){
        Thread{
            AppDatabase.getInstance(activity as MainActivity).placeDao().insertPlace(place)
        }.start()
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.fragment_add_fav_place)
//        setSupportActionBar(toolbar_add_place)
//        //Adding back button on toolbar
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        //Goes back when back button pressed
//        toolbar_add_place.setNavigationOnClickListener{
//            onBackPressed()
//        }
//    }

}