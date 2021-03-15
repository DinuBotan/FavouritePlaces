package com.project.favouriteplaces

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
            }else{
                ActivityCompat.requestPermissions(activity as MainActivity, arrayOf(android.Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
            }
        }

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
                startActivityForResult(intent, CAMERA_REQUEST_CODE)
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

}