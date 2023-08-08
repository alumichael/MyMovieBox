package com.homework.mymoviebox.presentation.home

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.homework.mymoviebox.R
import com.homework.mymoviebox.databinding.ActivityHomeBinding
import com.homework.mymoviebox.presentation.home.movie.MovieFragmentDirections
import com.homework.mymoviebox.util.changeFont
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarLayout.appBar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(),
            fallbackOnNavigateUpListener = {
                finish()
                true
            }
        )

        setSupportActionBar(binding.appBarLayout.appBar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        navController = navHostFragment.findNavController().run {
            findViewById<Toolbar>(R.id.appBar)
                .setupWithNavController(this, appBarConfiguration)
            this
        }


        navController = navHostFragment.findNavController().run {
            findViewById<Toolbar>(R.id.appBar)
                .setupWithNavController(this, appBarConfiguration)
            this
        }


        binding.appBarLayout.favourite.setOnClickListener {
            navController.navigate(
                MovieFragmentDirections.actionMovieFragmentToFavouriteFragment()
            )
        }

        navController.graph.setStartDestination(R.id.movieFragment)
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (controller.currentDestination?.id == R.id.movieFragment) {
                binding.appBarLayout.movie.changeFont(true)
                binding.appBarLayout.favourite.changeFont(false)
            } else {
                binding.appBarLayout.movie.changeFont(false)
                binding.appBarLayout.favourite.changeFont(true)

                binding.appBarLayout.movie.setOnClickListener {
                    navController.popBackStack()
                }
            }

        }
    }


    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp() || super.onSupportNavigateUp()
}
