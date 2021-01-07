package com.udacity.shoestore

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.udacity.shoestore.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private var authorized: Boolean = false
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var logoutMenu: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.d("onCreate")
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupNavigation()

        viewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel.loginLiveData.observe(this, { authorized ->
            Timber.d("auth changed to $authorized")
            this.authorized = authorized
            invalidateOptionsMenu()
        })
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(setOf(R.id.loginFragment, R.id.shoeListingFragment))
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        Timber.d("onCreateOptionsMenu")
        menuInflater.inflate(R.menu.menu, menu)
        logoutMenu = menu.findItem(R.id.menu_logout)
        logoutMenu?.isVisible = authorized
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_logout -> {
                viewModel.logout()
                navController.popBackStack(R.id.loginFragment, true)
                navController.navigate(R.id.loginFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
