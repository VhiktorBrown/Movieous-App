package com.theelitedevelopers.homeofmovies.presentation.home.fragments.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.theelitedevelopers.homeofmovies.R
import com.theelitedevelopers.homeofmovies.databinding.MovieItemBinding
import com.theelitedevelopers.homeofmovies.domain.models.Movie
import com.theelitedevelopers.homeofmovies.presentation.details.MovieDetailsActivity
import com.theelitedevelopers.homeofmovies.utils.Constants

/**
 * @created 17/5/2023 - 6:53 PM
 * @project Movies app
 * @author Victor
 */

class MovieAdapter(var context : Context, var movieList : List<Movie>?
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    var size = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding : MovieItemBinding = MovieItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        //set and display details of each movie
        holder.binding.movieTitle.text = movieList!![position].title
        holder.binding.movieRating.text = movieList!![position].rating.toString()

        Picasso.get()
            .load(Constants.BASE_URL_IMAGE+movieList!![position].image)
            .placeholder(R.drawable.image_12)
            .into(holder.binding.movieImage)

        holder.binding.root.setOnClickListener {

            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(Constants.MOVIE_ID, movieList!![position].id)
            it.context.startActivity(intent)
        }
    }

     @SuppressLint("NotifyDataSetChanged")
     fun setList(list : List<Movie>){
        movieList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(movieList!!.isNotEmpty()){
            movieList!!.size
        }else {
            size
        }
    }

    class MovieViewHolder(var binding : MovieItemBinding) : RecyclerView.ViewHolder(binding.root)
}