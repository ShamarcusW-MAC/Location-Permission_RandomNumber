package com.example.week4_day3_permission_randomnumber

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FirstRandom: Fragment() {


    interface FragmentListener{
        fun updateNumber()
    }

    fun setListener(fragmentListener: FragmentListener){
        this.fragmentListener = fragmentListener
    }

    lateinit var fragmentListener: FragmentListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.first_frame_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

}