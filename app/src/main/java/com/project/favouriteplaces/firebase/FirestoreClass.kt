package com.project.favouriteplaces.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.ktx.Firebase
import com.project.favouriteplaces.fragments.SignInFragment
import com.project.favouriteplaces.fragments.SignUpFragment
import com.project.favouriteplaces.models.User

class FirestoreClass {

    private val mFireStore = FirebaseFirestore.getInstance()

    fun registerUser(fragment: SignUpFragment, userInfo: User){
        Log.d("FirebaseD", "Inside register user")
        mFireStore.collection("Users")
            .document(getCurrentUserId()).set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                fragment.userRegisteredSuccess()
                Log.d("FirebaseD", "User registered successfully in firestore")
            }
                .addOnFailureListener{e ->
                    Log.d("FirebaseD", "User unregistered in firestore")
                    Log.e(
                            "FirebaseD",
                            "Error writing document",
                            e
                    )

                }

    }

    fun signInUser(fragment: SignInFragment){
        mFireStore.collection("Users")
                .document(getCurrentUserId())
                .get()
                .addOnSuccessListener {document ->
                    val logedInUser = document.toObject(User::class.java)

                    if(logedInUser != null) {
                        fragment.signInSuccess(logedInUser)
                    }
                }
                .addOnFailureListener{e ->
                    Log.e(
                            "errors",
                            "Error writing document",
                            e
                    )

                }
    }

    //We get the user id created when the user signs up
    fun getCurrentUserId(): String{
        Log.d("userId", "Inside getCurrentUserId()")
        if(FirebaseAuth.getInstance().currentUser != null) {
            Log.d("userId", "current user != null")
            Log.d("userId", FirebaseAuth.getInstance().currentUser!!.uid)
            return FirebaseAuth.getInstance().currentUser!!.uid
        }
        Log.d("userId", "current user == null")
        return "0"
    }
}