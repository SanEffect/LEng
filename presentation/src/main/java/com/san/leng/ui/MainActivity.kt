package com.san.leng.ui

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.san.leng.AndroidApplication
import com.san.leng.R
import com.san.leng.core.broadcastReceivers.NetworkBroadcastReceiver
import com.san.leng.databinding.ActivityMainBinding
import timber.log.Timber

class Handler {
    fun onNavigationClick(@NonNull item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.recordsFragment -> {
                Timber.i("This is RecordFragment -------------------------->")
            }
        }
        return true
    }
}

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var binding: ActivityMainBinding

    private val networkBroadcastReceiver = NetworkBroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as AndroidApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

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

        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.nav_host_fragment
        ) as NavHostFragment
        val navController = navHostFragment.navController

        // Setup the bottom navigation view with navController
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setupWithNavController(navController)

        // Setup the ActionBar with navController and 3 top level destinations
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.dashboardFragment,
                R.id.recordsFragment,
                R.id.dictionaryFragment,
                R.id.usefulInfoFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.recordsFragment -> {
                    binding.addRecord.show()
                }
                else -> {
                    binding.addRecord.hide()
                }
            }
        }


        // setup BottomNavigationView navigation
//        bottomNavigationView = findViewById(R.id.bottom_navigation)
//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)
//
//        navController.addOnDestinationChangedListener { _, destination, _ ->
//            Timber.i("destination: $destination")
//            if(destination.id == R.id.recordsFragment) {
//                Timber.i("This is RecordFragment -------------------------->")
//            }
//        }


//        binding.handler = Handler()

/*        bottomNavigationView.setOnNavigationItemSelectedListener { item ->

            val addRecordButton = findViewById<FloatingActionButton>(R.id.add_record)

            supportFragmentManager.beginTransaction().replace(item.itemId, RecordsFragment()).commit()
//            supportFragmentManager.beginTransaction().replace(R.id.recordsFragment, RecordsFragment()).commit()

            when(item.itemId) {
                R.id.recordsFragment -> addRecordButton.show()
                else -> addRecordButton.hide()
            }
            true
        }*/
    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//
//        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
//        NavigationUI.onNavDestinationSelected(item!!, navController)
//        return super.onOptionsItemSelected(item)
//    }

/*    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.recordsFragment -> {
                Timber.i("This is RecordFragment ------------------------------------------------------>")
            }
        }
        return true
    }*/
}
