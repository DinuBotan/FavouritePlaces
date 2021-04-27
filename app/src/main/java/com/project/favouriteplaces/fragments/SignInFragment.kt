package com.project.favouriteplaces.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_in.*
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*


class SignInFragment : BaseFragment() {

    companion object{
        const val TAG = "TAG_FRAGMENT_SIGNIN"
    }

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_sign_in, container, false)

        (activity as MainActivity).setSupportActionBar(rootView.toolbar_sign_in_fragment)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        rootView.toolbar_sign_in_fragment.setNavigationOnClickListener{
            (activity as MainActivity).onBackPressed()
        }

        auth = FirebaseAuth.getInstance()

        rootView.btn_sign_in.setOnClickListener{
            signInRegisteredUser()
        }

        return rootView

    }

    private fun signInRegisteredUser(){
        val email: String = et_email_sign_in.text.toString().trim{ it <= ' '}
        val password: String = et_password_sign_in.text.toString().trim{ it <= ' '}

        if(validateForm(email, password)){
            showProgressDialog(resources.getString(R.string.please_wait))

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity as MainActivity) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Sing in", "createUserWithEmail:success")
                        val user = auth.currentUser
                        (activity as MainActivity).showFragmentByTag(MainFragment.TAG)

                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Sing in", "createUserWithEmail:failure", task.exception)
                        Toast.makeText(activity as MainActivity, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun validateForm(email: String, password: String): Boolean{
        return when {
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