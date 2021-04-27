package com.project.favouriteplaces.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_add_fav_places.view.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


class SignUpFragment : BaseFragment() {

    companion object{
        const val TAG = "TAG_FRAGMENT_SIGNUP"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_up, container, false)

        (activity as MainActivity).window.setFlags(
              WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        //(activity as MainActivity).window.insetsController.hide(WindowInsets.Type.statusBars())


        (activity as MainActivity).setSupportActionBar(rootView.toolbar_sign_up_fragment)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rootView.toolbar_sign_up_fragment.setNavigationOnClickListener{
            (activity as MainActivity).onBackPressed()
        }

                rootView.btn_sign_up.setOnClickListener{
                    registerUser()
        }

        return rootView
    }

//    override  fun onClick(v: View?){
//        //check if the view id is the one we are interested in.
//        when(v!!.id){
//            R.id.btn_sign_up -> {
//                (activity as MainActivity).showFragmentByTag(TAG)
//            }
//
//        }
//    }

    private fun registerUser(){
        val name: String = et_name.text.toString().trim {it <= ' '}
        val email: String = et_email.text.toString().trim {it <= ' '}
        val password: String = et_password.text.toString()

        if(validateForm(name, email, password)){
        showProgressDialog(resources.getString(R.string.please_wait))
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                hideProgressDialog()
                if(task.isSuccessful){
                    (activity as MainActivity).showFragmentByTag(SignInFragment.TAG)
                    val firebaseUser: FirebaseUser = task.result!!.user!!
                    val registeredEmail = firebaseUser.email!!
                    Toast.makeText((activity as MainActivity), "$name you have successfully registered the email address $registeredEmail", Toast.LENGTH_LONG).show()
                    FirebaseAuth.getInstance().signOut()
                }else{
                    Toast.makeText(activity as MainActivity,
                            "Registration failed", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    private fun validateForm(name: String, email: String, password: String): Boolean{
        return when {
            TextUtils.isEmpty(name) ->{
                showErrorToast("Please enter a name")
                false
            }
            TextUtils.isEmpty(email) ->{
                showErrorToast("Please enter an email address")
                false
            }
            TextUtils.isEmpty(password) ->{
                showErrorToast("Please enter a password")
                false
            }else ->{
                true

            }
        }

    }


}