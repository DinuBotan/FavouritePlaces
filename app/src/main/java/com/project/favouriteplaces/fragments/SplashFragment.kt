package com.project.favouriteplaces.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import kotlinx.android.synthetic.main.fragment_sign_up.view.*

class SplashFragment : Fragment() {
    companion object{
        const val TAG = "TAG_FRAGMENT_SPLASH"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_splash, container, false)


        return rootView

    }
}