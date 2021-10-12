package com.madkins.extendmovies.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.madkins.extendmovies.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "PopularMoviesFragment"


class PopularMoviesFragment: Fragment(), PopularMoviesViewHolder.OnMovieClickListener {
    interface Callbacks {
        fun onMovieClick(id: Int)
    }

    private val viewModel by activityViewModels<MoviesViewModel>()
    private lateinit var recyclerView: RecyclerView
    private val adapter = PopularMoviesAdapter(this)
    private var callbacks: Callbacks? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callbacks = context as Callbacks?
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular_movies, container, false)
        recyclerView = view.findViewById(R.id.popular_movies_recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this.context, 2)
        recyclerView.adapter = adapter

        // This should be moved somewhere else to prevent restarting the network call on device rotation
        // Not sure where yet
        //if(viewModel.shouldLoad) {
        fetchPopularMovies()

        //viewModel.shouldLoad = false
        //}

        return view
    }
    override fun onDetach() {
        super.onDetach()
        //viewModel.shouldLoad = true
        callbacks = null
    }

    // Start the coroutine to fetch the popular movies from our API
    // And send them into our adapter
    private fun fetchPopularMovies() {
        lifecycleScope.launch {
            //lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.fetchPopularMovies().collectLatest { data ->
                    adapter.submitData(data)
                }
           // }
        }
    }

    override fun onMovieClick(id: Int) {
        callbacks?.onMovieClick(id)
    }
}