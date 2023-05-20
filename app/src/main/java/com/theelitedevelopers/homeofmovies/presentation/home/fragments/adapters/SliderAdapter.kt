package com.theelitedevelopers.homeofmovies.presentation.home.fragments.adapters

/**
 * @created 17/5/2023 - 6:53 PM
 * @project Movies app
 * @author Victor
 */

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import com.squareup.picasso.Picasso
import com.theelitedevelopers.homeofmovies.R
import com.theelitedevelopers.homeofmovies.domain.models.Movie
import com.theelitedevelopers.homeofmovies.utils.Constants
class SliderAdapter(var context: Context?, var movieArrayList: List<Movie>?): PagerAdapter() {
    var inflater: LayoutInflater? = null
    var view: View? = null

    override fun getCount(): Int {
        return movieArrayList!!.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = context!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View = inflater!!.inflate(R.layout.view_pager_item, container, false)
        val imageView = view.findViewById<ImageView>(R.id.movie_image)
        val titleTextView = view.findViewById<TextView>(R.id.movie_title)
        val dateTextView = view.findViewById<TextView>(R.id.movie_date)

        titleTextView.text = movieArrayList!![position].title
        dateTextView.text = movieArrayList!![position].releaseDate

        Picasso.get()
            .load(Constants.BASE_URL_IMAGE+movieArrayList!![position].image)
            .placeholder(R.drawable.image_12)
            .into(imageView)

        container.addView(view)

        //set click listener for imageView
        imageView.setOnClickListener {
//            val intent = Intent(context, EventDetailsActivity::class.java)
//            intent.putExtra(Constants.EVENT_DETAIL, eventDetailsArrayList.get(position))
//                .putExtra(Constants.EVENT_DETAIL_INFO, "main")
//            context!!.startActivity(intent)
        }
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}