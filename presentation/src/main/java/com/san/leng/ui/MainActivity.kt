package com.san.leng.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.san.leng.AndroidApplication
import com.san.leng.R
import com.san.leng.core.broadcastReceivers.NetworkBroadcastReceiver
import com.san.leng.core.extensions.setMargins
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val networkBroadcastReceiver = NetworkBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AndroidApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigation()
    }

    override fun onStart() {
        super.onStart()

        registerReceiver(
            networkBroadcastReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    override fun onStop() {
        super.onStop()

        unregisterReceiver(networkBroadcastReceiver)
    }

    private fun setupBottomNavigation() {

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.dashboardFragment,
                R.id.dictionaryFragment,
                R.id.usefulInfoFragment -> {
                    add_record_fab.hide()
                    bottom_bar.performShow()
                    bottom_navigation.setMargins(rightMarginDp = 0)
                }
                R.id.recordsFragment -> {
                    add_record_fab.show()
                    bottom_navigation.setMargins(rightMarginDp = 80)
                    bottom_bar.performShow()
                }
                else -> {
                    add_record_fab.hide()
                    bottom_bar.performHide()
                }
            }
        }
    }
}

