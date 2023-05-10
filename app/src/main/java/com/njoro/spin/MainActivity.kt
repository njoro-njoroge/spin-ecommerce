package com.njoro.spin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.njoro.spin.databinding.ActivityMainBinding
import com.njoro.spin.utils.ConnectivityObserver
import com.njoro.spin.utils.NetworkConnectivityObserver
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : AppCompatActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//navController=Navigation.findNavController(this,R.id.nav_host_fragment)
//        setupActionBarWithNavController(binding.navView.)

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
            println("Status is $it")
            if(it.toString() =="Online"){

                Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Network $it", Toast.LENGTH_SHORT).show()
            }

        }.launchIn(lifecycleScope)

        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.dashboardFragment,
                R.id.cartFragment,
                R.id.loginFragment
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        navView.menu.findItem(R.id.loginFragment).isVisible = false



    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun hideBottomNav() {
        navView.visibility = View.GONE
    }

    fun showBottomNav() {
        navView.visibility = View.VISIBLE
    }
}