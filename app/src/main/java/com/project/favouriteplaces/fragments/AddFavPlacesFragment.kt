package com.project.favouriteplaces.fragments
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context

import android.content.Context.LOCATION_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Looper
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import com.google.android.gms.location.*
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import com.project.favouriteplaces.database.AppDatabase
import com.project.favouriteplaces.database.FavPlace
import com.project.favouriteplaces.utils.GetAddressFromLatLng
import kotlinx.android.synthetic.main.fragment_add_fav_places.*
import kotlinx.android.synthetic.main.fragment_add_fav_places.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.util.*
import java.util.jar.Manifest

class AddFavPlacesFragment : Fragment(), View.OnClickListener {

    private var saveImageToInternalStorage : Uri? = null
    private var pLatitude : Double = 0.0
    private var pLongitude : Double = 0.0

    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    companion object{
        const val TAG = "TAG_FRAGMENT_ADD_PLACES"
        private const val GALLERY = 1
        private const val CAMERA = 2
        //Place where we will store the images.
        private const val IMAGE_DIRECTORY = "FavPlacesImages"
        private const val PLACE_AUTOCOMPLETE_REQUEST_CODE = 3
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(activity as MainActivity)

        if(!Places.isInitialized()){
            Places.initialize(activity as MainActivity, resources.getString(R.string.google_maps_api_key))
        }

//        rootView.btn_save.setOnClickListener{
//            var newPlace = Place(null, et_title.text.toString(), til_description.toString(), et_location.toString(), "0", 0.0 ,0.0)
//            savePlace(newPlace)
//        }

        rootView.tv_add_image.setOnClickListener(this)
        rootView.btn_save.setOnClickListener(this)
        rootView.et_location.setOnClickListener(this)
        rootView.tv_select_current_location.setOnClickListener(this)



        return rootView
    }

//    private fun isLocationEnabled(): Boolean{
//        val locationManager: LocationManager =
//                getSystemService(Context, LOCATION_SERVICE) as LocationManager
//        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
//    }

    private fun isLocationEnabled(mContext: Context): Boolean {
    val lm = mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return lm.isProviderEnabled(LocationManager.GPS_PROVIDER) || lm.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER)
}

    //We supress the error because we will have checked for bermission by this time
    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 1000
        //We only want to get the location once, not continuously
        mLocationRequest.numUpdates = 1

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, Looper.myLooper())
    }

    private val mLocationCallBack = object : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            val mLastLocation: Location = locationResult!!.lastLocation
            pLatitude = mLastLocation.latitude
            Log.i("Current latitude", "$pLatitude")
            pLongitude = mLastLocation.longitude
            Log.i("Current longitude", "$pLongitude")

            val addressTask = GetAddressFromLatLng(activity as MainActivity, pLatitude, pLongitude)
            addressTask.setAddressListener(object: GetAddressFromLatLng.AddressListener{
               override fun onAddressFound(address:String?){
                   et_location.setText(address)

               }

                override fun onError(){
                    Log.e("Get address::", "Something went wrong")
                }
            })
            addressTask.getAddress()

        }
    }

    private fun savePlace(favPlace: FavPlace){
        Thread{
            AppDatabase.getInstance(activity as MainActivity).placeDao().insertPlace(favPlace)
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
            R.id.btn_save -> {
                when {
                    et_title.text.isNullOrEmpty() -> {
                        Toast.makeText(
                            activity as MainActivity,
                            "Please enter a title",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    et_description.text.isNullOrEmpty() -> {
                        Toast.makeText(
                            activity as MainActivity,
                            "Please enter a description",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    et_location.text.isNullOrEmpty() -> {
                        Toast.makeText(
                            activity as MainActivity,
                            "Please enter a location",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    saveImageToInternalStorage == null -> {
                        Toast.makeText(
                            activity as MainActivity,
                            "Please select an image",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else ->{
                    var newPlace = FavPlace(null, et_title.text.toString(), et_description.text.toString(), et_location.text.toString(),
                        saveImageToInternalStorage.toString(), pLatitude ,pLongitude)
                    savePlace(newPlace)
                    onPause()
                    onStop()
                    onDestroy()
                    (activity as MainActivity).showFragmentByTag(MainFragment.TAG)

                }
                }
            }
            R.id.et_location ->{
                try{
                    //The fields we need to pass
                    val fields = listOf(
                            Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG, Place.Field.ADDRESS
                    )

                    val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(activity as MainActivity)
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE)
                }catch(e:Exception){
                    e.printStackTrace()
                }
            }
            R.id.tv_select_current_location -> {
                if(!isLocationEnabled(activity as MainActivity)){
                    Toast.makeText(
                            activity as MainActivity,
                            "Your location is turned off, Please turn it on",
                            Toast.LENGTH_SHORT
                    ).show()

                }else{
                    Dexter.withActivity(activity as MainActivity).withPermissions(
                            android.Manifest.permission.ACCESS_FINE_LOCATION,
                            android.Manifest.permission.ACCESS_COARSE_LOCATION
                    ).withListener(object : MultiplePermissionsListener {
                        override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                            if (report!!.areAllPermissionsGranted()) {

                                 requestNewLocationData()
                            }
                        }

                        override fun onPermissionRationaleShouldBeShown(
                                permissions: MutableList<PermissionRequest>?,
                                token: PermissionToken?
                        ) {
                            showRationalDialogForPermissions()
                        }
                    }).onSameThread()
                            .check()
                }
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

                        saveImageToInternalStorage = saveImageToInternalStorage(selectedImageBitmap)

                        Log.e("Saved image: ", "Path :: $saveImageToInternalStorage")

                        //saving the image in the internal storage
                        saveImageToInternalStorage(selectedImageBitmap)
                        iv_place_image.setImageBitmap(selectedImageBitmap)
                    }catch(e: IOException){
                        e.printStackTrace()
                        Toast.makeText(activity as MainActivity, "Failed to load the image from Gallery", Toast.LENGTH_SHORT).show()
                    }
                }
            } else if(requestCode == CAMERA){
                //we take the data (image) and convert it into a bitmap.
                val thumbnail : Bitmap = data!!.extras!!.get("data") as Bitmap

                saveImageToInternalStorage = saveImageToInternalStorage(thumbnail)

                Log.e("Saved image: ", "Path :: $saveImageToInternalStorage")

                iv_place_image.setImageBitmap(thumbnail)
            }else if(requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE){
                val place: Place = Autocomplete.getPlaceFromIntent(data!!)
                et_location.setText(place.address)
                pLatitude = place.latLng!!.latitude
                pLongitude = place.latLng!!.longitude
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

    //Will return the Uri, the location of the image.
    private fun saveImageToInternalStorage(bitmap: Bitmap):Uri{
        val wrapper = ContextWrapper((activity as MainActivity).applicationContext)
            //Mode private - makes directory only accessible to this app.
        var file = wrapper.getDir(IMAGE_DIRECTORY, Context.MODE_PRIVATE)
        // gives a unique name to each image.
        file = File(file, "${UUID.randomUUID()}.jpg")

        try{
            val stream : OutputStream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            stream.flush()
            stream.close()
        }catch (e: IOException){
            e.printStackTrace()
        }
        return Uri.parse(file.absolutePath)
    }








}