package com.theelitedevelopers.homeofmovies.presentation.details.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.theelitedevelopers.homeofmovies.R
import com.theelitedevelopers.homeofmovies.databinding.CastItemBinding
import com.theelitedevelopers.homeofmovies.databinding.MovieItemBinding
import com.theelitedevelopers.homeofmovies.domain.models.Cast
import com.theelitedevelopers.homeofmovies.domain.models.Movie
import com.theelitedevelopers.homeofmovies.utils.Constants

/**
 * @created 17/5/2023 - 6:53 PM
 * @project Movies app
 * @author Victor
 */

class CastAdapter(var context : Context, var castList : List<Cast>?
) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {

    var size = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding : CastItemBinding = CastItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        //set and display details of each movie
        holder.binding.castOriginalName.text = castList!![position].name
        holder.binding.castCharacterName.text = castList!![position].character

        Picasso.get()
            .load(Constants.BASE_URL_IMAGE+castList!![position].profilePath)
            .placeholder(R.drawable.image_12)
            .into(holder.binding.castImage)

        holder.binding.root.setOnClickListener {
//            val intent = Intent(context, ProductDetailActivity::class.java)
//                .putExtra(Constants.MOVIE_DETAIL, movieList[position])
//            context.startActivity(intent)
        }
    }

     @SuppressLint("NotifyDataSetChanged")
     fun setList(list : List<Cast>){
        castList = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if(castList!!.isNotEmpty()){
            castList!!.size
        }else {
            size
        }
    }

    class CastViewHolder(var binding : CastItemBinding) : RecyclerView.ViewHolder(binding.root)
}