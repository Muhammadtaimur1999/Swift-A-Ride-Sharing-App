package com.example.swift.frontEnd.activities

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.swift.R
import com.example.swift.businessLayer.BroadCasts.InternetConnectivityBroadcastReceiver
import com.example.swift.businessLayer.session.RiderSession
import com.example.swift.frontEnd.fragments.RiderDisplayInformationFragment
import com.example.swift.frontEnd.fragments.RiderHomePageFragment
import com.example.swift.frontEnd.fragments.RiderOfferListFragment
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_rider_main.*


class RiderMainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    private val connectivityInternet: BroadcastReceiver = InternetConnectivityBroadcastReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RiderSession.getCurrentUser { rider ->
            val email = rider.email
            val phonneNumber = rider.phoneNumber
        }
        requestedOrientation =  (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContentView(R.layout.activity_rider_main)
        supportActionBar?.hide()

        activate_menu.setOnClickListener {
            rider_drawer.openDrawer(GravityCompat.START)
            activate_menu.hide()
        }

        //setting default fragment
        supportFragmentManager.beginTransaction().replace(R.id.rider_main_fragment_container, RiderHomePageFragment()).addToBackStack(null).commit()
        rider_nav_view.setCheckedItem(R.id.nav_home)

        rider_drawer.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                activate_menu.hide()
                rider_drawer.bringToFront()
            }

            override fun onDrawerOpened(drawerView: View) {
                activate_menu.hide()
                rider_drawer.bringToFront()
            }

            override fun onDrawerClosed(drawerView: View) {
                activate_menu.show()
                rider_main_fragment_container.bringToFront()
            }

            override fun onDrawerStateChanged(newState: Int) {
                //Log.i(TAG, "onDrawerStateChanged");
            }
        })


        rider_becomeDriver_btn.setOnClickListener {
            //checking if the rider already registered as a driver or not
            RiderSession.getCurrentUser { rider ->
                if(rider.isdriver == "true") {
                    startActivity(Intent(this, DriverMainActivity::class.java))
                }
                else {
                    startActivity(Intent(this, DriverRegistrationActivity::class.java))
                }
            }
        }

        rider_nav_view.setNavigationItemSelectedListener(this)
    }

    //for broadcasting
    override fun onStart() {
        super.onStart()
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(connectivityInternet, filter)
    }
    override fun onStop() {
        super.onStop()
        unregisterReceiver(connectivityInternet)
    }

    override fun onBackPressed() {
        if(rider_drawer.isDrawerOpen(GravityCompat.START)){
            rider_drawer.closeDrawer(GravityCompat.START)
            activate_menu.show()
        }else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> {
                supportFragmentManager.beginTransaction().replace(R.id.rider_main_fragment_container, RiderHomePageFragment()).addToBackStack(null).commit()
            }
            R.id.nav_userInfo ->{
                supportFragmentManager.beginTransaction().replace(R.id.rider_main_fragment_container, RiderDisplayInformationFragment()).addToBackStack(null).commit()
            }
            R.id.nav_offers -> {
                supportFragmentManager.beginTransaction().replace(R.id.rider_main_fragment_container, RiderOfferListFragment()).addToBackStack(null).commit()
            }
            R.id.nav_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this,SignInActivity::class.java))
                finish()
            }
        }

        rider_drawer.closeDrawer(GravityCompat.START)
        return true
    }

}