package com.madkins.extendmovies.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.madkins.extendmovies.R
import com.madkins.extendmovies.model.Movie
import com.madkins.extendmovies.repo.TMDBRepo
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

const val TAG = "DetailedMovieFragment"

class DetailedMovieFragment: Fragment() {
    private val viewModel by activityViewModels<MoviesViewModel>()
    private lateinit var poster: ImageView
    private lateinit var title: TextView
    private lateinit var overview: TextView
    private lateinit var date: TextView
    private lateinit var revenue: TextView
    private lateinit var layout: FrameLayout
    private lateinit var upButton: ImageButton
    private lateinit var favoriteButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Forcing this screen into portrait mode for the time being, not a good workaround
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        return inflater.inflate(R.layout.fragment_detailed_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        poster = view.findViewById(R.id.detailed_movie_poster)
        title = view.findViewById(R.id.detailed_movie_title)
        overview = view.findViewById(R.id.detailed_movie_overview)
        date = view.findViewById(R.id.detailed_movie_release_date)
        revenue = view.findViewById(R.id.detailed_movie_revenue)
        layout = view.findViewById(R.id.detailed_movie_upper_layout)
        upButton = view.findViewById(R.id.detailed_movie_up_button)
        favoriteButton = view.findViewById(R.id.detailed_movie_favorite_button)

        upButton.setOnClickListener {
            // Navigate back to the popular movies fragment
            requireActivity().supportFragmentManager.popBackStack()
        }

        favoriteButton.setOnClickListener {
            viewModel.movieFavoriteButtonClicked()
        }

        // Observe the movie livedata as it comes back from the API
        // And update the UI with appropriate fields
        viewModel.movieLiveData.observe(
            viewLifecycleOwner,
            { movie ->
                movie?.let {
                    updateUi(movie)
                }
            }
        )
    }

    override fun onDestroyView() {
        // Releasing the forced portrait mode
        requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        super.onDestroyView()
    }

    private fun updateUi(movie: Movie) {
        val backdropUrl = TMDBRepo.IMAGE_URL_BASE + movie.backdropUrl
        val posterUrl = TMDBRepo.IMAGE_URL_BASE + movie.posterUrl
        val img = ImageView(this.context)

        // Get the backdrop image and try to attach it to the background of the upper layout
        Picasso.get()
            .load(backdropUrl)
            .into(img, object: Callback {
                override fun onSuccess() {
                    layout.background = img.drawable
                }

                override fun onError(e: Exception?) {
                    Log.d(TAG, "Failed to attach image to layout background")
                }
            })
        Picasso.get()
            .load(posterUrl)
            .into(poster)

        title.text = movie.title
        overview.text = movie.overview
        date.text = movie.releaseDate
        revenue.text = "$${movie.revenue}"
        if(movie.favorited) {favoriteButton.text = "Favorited"} else {favoriteButton.text = "Favorite"}
    }
}