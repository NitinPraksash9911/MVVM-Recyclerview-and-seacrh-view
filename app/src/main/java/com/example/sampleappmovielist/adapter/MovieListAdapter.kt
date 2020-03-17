package com.example.sampleappmovielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.sampleappmovielist.databinding.ListItemViewBinding
import com.example.sampleappmovielist.model.Datum
import com.example.sampleappmovielist.utils.MovieListDiffCallBack
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Nitin  on 17/03/20.
 */
class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>(), Filterable {

    private var movieList = arrayListOf<Datum>()
    private var movieListFull = arrayListOf<Datum>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {

        val binding =
            ListItemViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return movieList.size
    }

    fun setMovieList(movielistData: ArrayList<Datum>) {
        val diffCallback = MovieListDiffCallBack(this.movieList, movielistData)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.movieList.clear()
        this.movieList.addAll(movielistData)
        movieListFull = ArrayList(movieList)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {

        holder.bindTo(movieList.get(position))
    }

    class MovieListViewHolder(var binding: ListItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindTo(data: Datum) {
            binding.movieData = data
            binding.executePendingBindings()
        }

    }

    override fun getFilter(): Filter {
        return movieListFilter
    }

    private var movieListFilter = object : Filter() {
        private val filterResults = FilterResults()
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = arrayListOf<Datum>()
            if (constraint.isNullOrBlank()) {
                filteredList.addAll(movieListFull)
            } else {
                val query = constraint.toString().toLowerCase(Locale.ROOT).trim()
                val searchResults = movieListFull.filter { data ->
                    data.genre.toLowerCase(Locale.ROOT).contains(query) || data.title.toLowerCase(
                        Locale.ROOT
                    ).contains(query)
                }
                filteredList.addAll(searchResults)

            }
            filterResults.values = filteredList
            return filterResults
        }

        override fun publishResults(constraint: CharSequence?, result: FilterResults?) {

            movieList.clear()
            movieList.addAll(result!!.values as Collection<Datum>)
            notifyDataSetChanged()
        }


    }

}


