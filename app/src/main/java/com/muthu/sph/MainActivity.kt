package com.muthu.sph

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //set up nav bar
        setupNavBar()
    }


    /**
     * Initializing the nav bar
     * and setting the nav bar to navigation UI
     */
    private fun setupNavBar() {
        navController = Navigation.findNavController(this, R.id.sphMainFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }


    /**
     * when moving to the details screen or any other screen from
     * home screen the below overridden function will take care of the navigation history
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)

    }
}