package com.example.week4_day3_permission_randomnumber

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.first_frame_layout.*
import kotlinx.android.synthetic.main.second_frame_layout.*

class MainActivity : AppCompatActivity(), FirstRandom.FragmentListener, SecondRandom.FragmentListener {

    private val requestCode = 800

    private var randomNum: Int = 0

    lateinit var random1: FirstRandom
    lateinit var random2: SecondRandom

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED)
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf("android.permission.ACCESS_FINE_LOCATION"), requestCode)
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf("android.permission.ACCESS_FINE_LOCATION"), requestCode)
            }

        }else {

            Log.d("TAG_X", "Permission granted")

        }
        showFragments()
        random_button.visibility = View.VISIBLE
        random_button.setOnClickListener {
            randomNum = (0..10000).random()

            updateNumber()
        }
        settings_imageView.setOnClickListener {
            val intent = Intent("android.settings.APPLICATION_SETTINGS")
            startActivity(intent)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == this.requestCode && permissions[0] == "android.permission.ACCESS_FINE_LOCATION"
            && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {
            Toast.makeText(this, "Permission is now within you power!", Toast.LENGTH_SHORT).show()
        }else {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.ACCESS_FINE_LOCATION"))
            {
                ActivityCompat.requestPermissions(this, arrayOf("android.permission.ACCESS_FINE_LOCATION"), requestCode)
            }else {
                Toast.makeText(this, "Location permission must be within your power to use this application! Click on the gear icon in the upper right corner!", Toast.LENGTH_SHORT).show()
                settings_imageView.visibility = View.VISIBLE

            }
        }
    }

    private fun showFragments() {
        random1 = FirstRandom()
        random2 = SecondRandom()

        supportFragmentManager.beginTransaction()
            .add(R.id.first_frame_layout, random1)
            .commit()

        supportFragmentManager.beginTransaction()
            .add(R.id.second_frame_layout, random2)
            .commit()
    }


    override fun onAttachFragment(fragment: Fragment) {
        super.onAttachFragment(fragment)
        if (fragment is FirstRandom)
            (fragment).setListener(this)
        else
            (fragment as SecondRandom).setListener(this)
    }

    override fun updateNumber() {
        first_layout_textview.text = randomNum.toString()
        second_layout_textview.text = randomNum.toString()
    }
}
