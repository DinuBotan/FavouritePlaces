package com.project.favouriteplaces.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.project.favouriteplaces.R
import com.project.favouriteplaces.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_intro.view.*

class IntroFragment : Fragment() {
    companion object{
        const val TAG = "TAG_FRAGMENT_INTRO"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_intro, container, false)

        rootView.btn_sign_in.setOnClickListener{
            (activity as MainActivity).showFragmentByTag(SignInFragment.TAG)
        }

        rootView.btn_sign_up.setOnClickListener{
            (activity as MainActivity).showFragmentByTag(SignUpFragment.TAG)
        }

        return rootView

    }
}