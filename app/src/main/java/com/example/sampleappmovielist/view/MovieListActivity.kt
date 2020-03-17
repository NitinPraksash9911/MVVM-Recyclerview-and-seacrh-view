package com.example.sampleappmovielist.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.example.sampleappmovielist.R
import com.example.sampleappmovielist.adapter.MovieListAdapter
import com.example.sampleappmovielist.databinding.ActivityMainBinding
import com.example.sampleappmovielist.model.Datum
import com.example.sampleappmovielist.viewmodel.MovieListViewModel
import com.google.android.material.snackbar.Snackbar


class MovieListActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MovieListViewModel
    lateinit var movieListAdapter: MovieListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        init()
    }

    private fun init() {
        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        binding.movieRecyclerView.apply {
            movieListAdapter = MovieListAdapter()
            adapter = movieListAdapter
        }

        //data observer
        initObserver()

    }

    private fun initObserver() {

        viewModel.getMovieListData()

        viewModel.movieLiveData.observe(this) {
            if (it != null && it.error.isNullOrBlank()) {
                binding.progressHorizontal.visibility = View.GONE
                movieListAdapter.setMovieList(it.data as ArrayList<Datum>)



            } else {
                binding.progressHorizontal.visibility = View.VISIBLE
                Snackbar.make(
                    binding.movieRecyclerView,
                    it.error,
                    Snackbar.LENGTH_LONG
                ).show()
            }


        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)
        searchViewInit(menu)

        return true
    }

    /** search view initialization **/
    private fun searchViewInit(menu: Menu?) {
        val searchItem = menu?.findItem(R.id.search_action)
        val searchView = searchItem!!.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        // to search item from list
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(text: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(text: String?): Boolean {
                movieListAdapter.filter.filter(text)
                return true
            }

        }
        )

        // return all list items when search view collapsed
        searchItem.setOnActionExpandListener(object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(menuItem: MenuItem): Boolean {
                return true
            }

            override fun onMenuItemActionCollapse(menuItem: MenuItem): Boolean {

                searchView.setQuery("", true)
                return true
            }
        })
    }


}
