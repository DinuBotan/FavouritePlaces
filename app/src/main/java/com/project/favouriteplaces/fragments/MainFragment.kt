package com.project.favouriteplaces.fragments

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.adapters.FavPlacesAdapter
import com.project.favouriteplaces.database.AppDatabase
import com.project.favouriteplaces.database.FavPlace
import kotlinx.android.synthetic.main.fragment_main2.*
import kotlinx.android.synthetic.main.fragment_main2.view.*


class MainFragment : Fragment() {

    companion object{
        const val TAG = "TAG_FRAGMENT_MAIN"
                //For permission code
        private const val CAMERA_PERMISSION_CODE = 1
        //For intent
        private const val CAMERA_REQUEST_CODE = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_main2, container, false)

        rootView.fabAddFavPlace.setOnClickListener{
            (activity as MainActivity).showFragmentByTag(AddFavPlacesFragment.TAG)
        }

                rootView.btn_camera.setOnClickListener{
            //Check if we have permissions
            if(ContextCompat.checkSelfPermission(activity as MainActivity, android.Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED)
            {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,
                    CAMERA_REQUEST_CODE
                )
            }else{
                ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(android.Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_CODE
                )
            }
        }

        Thread{

            var getFavPlaceList : ArrayList<FavPlace> = AppDatabase.getInstance(activity as MainActivity).placeDao().getPlaces().toCollection(ArrayList())


            (activity as MainActivity).runOnUiThread {
                if (getFavPlaceList.size > 0) {
                    setupFavPlacesRecyclerView(getFavPlaceList)

                    rv_fav_places_list.visibility = View.VISIBLE
                    tv_no_records_available.visibility = View.GONE
                } else {
                    rv_fav_places_list.visibility = View.GONE
                    tv_no_records_available.visibility = View.VISIBLE
                }
            }
        }.start()

        return rootView
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == CAMERA_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent,
                    CAMERA_REQUEST_CODE
                )
            }else{
                Toast.makeText(activity, "Permission was denied", Toast.LENGTH_LONG).show()
            }
        }
    }

    //Automatically called when user makes an image with camera
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == CAMERA_REQUEST_CODE){
                val thumBnail: Bitmap = data!!.extras!!.get("data") as Bitmap
                iv_image.setImageBitmap(thumBnail)
            }
        }


    }

    private fun setupFavPlacesRecyclerView(favPlaceList: ArrayList<FavPlace>){
        rv_fav_places_list.layoutManager = LinearLayoutManager(activity as MainActivity)
        rv_fav_places_list.setHasFixedSize(true)

        val placesAdapter = FavPlacesAdapter(activity as MainActivity, favPlaceList)
        rv_fav_places_list.adapter = placesAdapter

        placesAdapter.setOnClickListener(object : FavPlacesAdapter.OnClickListener{
            override fun onClick(position: Int, model: FavPlace) {


//                var bundle = Bundle()
//                bundle.putParcelable("placeInfo", model)


                var bundle = Bundle()
                bundle.putSerializable("placeInfo", model)
                var fr = FavPlaceDetail()
                fr.arguments = bundle

                fragmentManager?.beginTransaction()?.replace(R.id.mainFragment, fr)?.commit()

                //(activity as MainActivity).showFragmentByTag(fr)

            }

        })




    }

}