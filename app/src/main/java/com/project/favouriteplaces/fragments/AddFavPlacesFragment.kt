package com.project.favouriteplaces.fragments
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.PermissionRequest
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.database.AppDatabase
import com.project.favouriteplaces.database.Place
import kotlinx.android.synthetic.main.fragment_add_fav_places.*
import kotlinx.android.synthetic.main.fragment_add_fav_places.view.*
import java.io.IOException
import java.lang.Appendable
import java.util.jar.Manifest

class AddFavPlacesFragment : Fragment(), View.OnClickListener {

    companion object{
        const val TAG = "TAG_FRAGMENT_ADD_PLACES"
        private const val GALLERY = 1
        private const val CAMERA = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_add_fav_places, container, false)

        (activity as MainActivity).setSupportActionBar(rootView.toolbar_add_place)
       (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rootView.toolbar_add_place.setNavigationOnClickListener{
            (activity as MainActivity).onBackPressed()
        }

        rootView.btn_save.setOnClickListener{
            var newPlace = Place(null, et_title.text.toString(), til_description.toString(), et_location.toString())
            savePlace(newPlace)
        }

        rootView.tv_add_image.setOnClickListener(this)


        return rootView;
    }

    private fun savePlace(place: Place){
        Thread{
            AppDatabase.getInstance(activity as MainActivity).placeDao().insertPlace(place)
        }.start()
    }

    //Runs every time something is clicked. With this function we take care of the on click events.
    override  fun onClick(v: View?){
        //check if the view id is the one we are interested in.
        when(v!!.id){

            R.id.tv_add_image -> {
                val pictureDialog = AlertDialog.Builder(activity as MainActivity)
                pictureDialog.setTitle("Select Action")
                val pictureDialogItems = arrayOf("Photo from Gallery", "Capture photo with Camera")
                pictureDialog.setItems(pictureDialogItems){
                    dialog, which ->
                    when(which){
                        0 -> choosePhotoFromGallery()
                            1 -> takePhotoFromCamera()

                    }
                }
                pictureDialog.show()
            }
        }
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == GALLERY){
                if(data != null){
                    //In case we have an image
                    val contentURI = data.data
                    try{
                        val selectedImageBitmap = MediaStore.Images.Media.getBitmap((activity as MainActivity).contentResolver, contentURI)
                        iv_place_image.setImageBitmap(selectedImageBitmap)
                    }catch(e: IOException){
                        e.printStackTrace()
                        Toast.makeText(activity as MainActivity, "Failed to load the image from Gallery", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if(requestCode == CAMERA){
                //we take the data (image) and convert it into a bitmap.
                val thumbnail : Bitmap = data!!.extras!!.get("data") as Bitmap
                iv_place_image.setImageBitmap(thumbnail)
            }
        }
    }

    private fun takePhotoFromCamera(){
        Dexter.withActivity(activity as MainActivity).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA
        ).withListener(object: MultiplePermissionsListener{
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if(report!!.areAllPermissionsGranted()){
                    val galleryIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(galleryIntent, CAMERA)
                }
            }
            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                token: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()
    }

    private fun choosePhotoFromGallery(){
        Dexter.withActivity(activity as MainActivity).withPermissions(
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object: MultiplePermissionsListener{
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if(report!!.areAllPermissionsGranted()){
                   val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                }
            }
            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
                token: PermissionToken?
            ) {
                showRationalDialogForPermissions()
            }
        }).onSameThread().check()

    }

    private fun showRationalDialogForPermissions(){
        AlertDialog.Builder(activity as MainActivity).setMessage("It looks like you have turned off the required permissions. Please enable in the app settings.")
            .setPositiveButton("Go to settings"){
                _, _ ->
                try{
                    //Add ssp to go to app settings
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", "null", null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException){
                    e.printStackTrace()
                }
            }.setNegativeButton("Cancel"){dialog, _ ->
                dialog.dismiss()
            }.show()
    }



}