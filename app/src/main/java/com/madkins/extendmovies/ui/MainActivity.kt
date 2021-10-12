package com.madkins.extendmovies.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.madkins.extendmovies.R

class MainActivity : AppCompatActivity(), PopularMoviesFragment.Callbacks {
    private val viewModel by viewModels<MoviesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Don't load the initial fragment unless the saved state is null
        // Otherwise we reload on every rotation
        if(savedInstanceState == null) {
            // Load the popular movies fragment by default
            loadFragment(PopularMoviesFragment())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onMovieClick(id: Int) {
        // Fetch the movie into LiveData in the viewmodel
        // So that our new fragment can observe it on load
        viewModel.fetchMovieById(id)
        loadFragment(DetailedMovieFragment())
    }
}