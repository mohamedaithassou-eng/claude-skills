package com.padeldarkar.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.padeldarkar.R
import com.padeldarkar.auth.LoginActivity
import com.padeldarkar.databinding.ActivityHomeBinding
import com.padeldarkar.utils.SessionManager

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigation.setupWithNavController(navController)

        binding.btnDeconnexion.setOnClickListener {
            SessionManager(this).deconnecter()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
