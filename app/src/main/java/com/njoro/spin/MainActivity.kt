package com.njoro.spin

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.njoro.ecommerce.utils.SessionManager
import com.njoro.spin.databinding.ActivityMainBinding
import com.njoro.spin.ui.auth.login.AuthViewModel
import com.njoro.spin.ui.home.HomeFragmentDirections
import com.njoro.spin.utils.ConnectivityObserver
import com.njoro.spin.utils.NetworkConnectivityObserver
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class MainActivity : AppCompatActivity() {

    private lateinit var connectivityObserver: ConnectivityObserver
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: BottomNavigationView
    private lateinit var viewModel: AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//navController=Navigation.findNavController(this,R.id.nav_host_fragment)
//        setupActionBarWithNavController(binding.navView.)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

        connectivityObserver = NetworkConnectivityObserver(applicationContext)
        connectivityObserver.observe().onEach {
            println("Status is $it")
            if (it.toString() == "Online") {

                Toast.makeText(this, "Online", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Network $it", Toast.LENGTH_SHORT).show()
            }

        }.launchIn(lifecycleScope)

        navView = binding.navView

        bottomNavigation()
        checkUserSession()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bottomNavigation(){
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)?.findNavController()

        navController?.let { controller ->
            bottomNavigationView.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.homeFragment -> {
                        controller.navigate(R.id.homeFragment)
                        true
                    }
                    R.id.dashboardFragment -> {
                        controller.navigate(R.id.dashboardFragment)
                        true
                    }
                    R.id.loginFragment->{
                        controller.navigate(R.id.loginFragment)
                        true
                    }
                    R.id.cartFragment->{
                        controller.navigate(R.id.cartFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }


     fun checkUserSession() {
         viewModel.loginState()
        viewModel.isUserLoggedIn.observe(this) { userLogin ->
            navView.menu.findItem(R.id.loginFragment).isVisible = !userLogin

        }
    }



    fun hideBottomNav() {
        navView.visibility = View.GONE
    }

    fun showBottomNav() {
        navView.visibility = View.VISIBLE
    }
}