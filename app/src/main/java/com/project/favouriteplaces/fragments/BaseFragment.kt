package com.project.favouriteplaces.fragments

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


open class BaseFragment : Fragment() {

    companion object{
        const val TAG = "TAG_FRAGMENT_BASE"
    }

    private var doubleBackToExitPressedOnce = false
    private lateinit var mProgressDialog: Dialog

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_base, container, false)

        (activity as MainActivity).setSupportActionBar(rootView.toolbar_sign_up_fragment)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rootView.toolbar_sign_up_fragment.setNavigationOnClickListener{
            (activity as MainActivity).onBackPressed()
        }





        return rootView

    }

    fun showProgressDialog(text: String){
        mProgressDialog = Dialog((activity as MainActivity))

        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.tv_progress_text.text = text
        mProgressDialog.show()
    }

    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun getCurrentUserID(): String{
        return FirebaseAuth.getInstance().currentUser!!.uid
    }

    fun doubleBackToExit(){
        if(doubleBackToExitPressedOnce){
            (activity as MainActivity).onBackPressed()
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(
                (activity as MainActivity),
                resources.getString(R.string.please_click_back_again_to_exit),
                Toast.LENGTH_SHORT
        ).show()

        Handler().postDelayed({doubleBackToExitPressedOnce = false}, 2000)
    }

    fun showErrorToast(message: String){
        Toast.makeText(
                (activity as MainActivity),
                message,
                Toast.LENGTH_LONG
        ).show()
    }

}