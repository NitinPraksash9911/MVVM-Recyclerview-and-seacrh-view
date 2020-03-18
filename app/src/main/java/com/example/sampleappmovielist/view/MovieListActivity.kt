package com.example.sampleappmovielist.view

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import com.example.sampleappmovielist.network.NetworkResponse
import com.example.sampleappmovielist.viewmodel.MovieListViewModel
import com.google.android.material.snackbar.Snackbar


class MovieListActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var viewModel: MovieListViewModel
    lateinit var movieListAdapter: MovieListAdapter
    lateinit var searchView: SearchView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        setSupportActionBar(binding.toolBar)

        /*initialization*/
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


            if (it != null && it.response == NetworkResponse.FROM_CACHE || it.response == NetworkResponse.SUCCESS) {
                binding.progressHorizontal.visibility = View.GONE
                movieListAdapter.setMovieList(it.data as ArrayList<Datum>)

            } else {
                binding.progressHorizontal.visibility = View.VISIBLE
            }
            /*to check from where data is coming cache or webservice
                                  * and check is internet is available or not
                                  * */
            showResponseStatus(it.response!!)

        }

    }

    private fun showResponseStatus(networkResponse: NetworkResponse) {

        when (networkResponse) {
            NetworkResponse.SUCCESS -> showSnackBar(getString(R.string.success), Color.GREEN)
            NetworkResponse.FROM_CACHE -> showSnackBar(
                getString(R.string.from_cache),
                Color.DKGRAY
            )
            NetworkResponse.NO_INTERNET -> showSnackBar(
                getString(R.string.no_internet),
                Color.RED
            )
            NetworkResponse.OTHERS -> showSnackBar(getString(R.string.other_error), Color.RED)
        }
    }

    private fun showSnackBar(status: String, bgColor: Int) {
        val snackbar = Snackbar.make(
            binding.mainLayout,
            status,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(bgColor)
        snackbar.show()
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
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = (searchItem!!.actionView as SearchView).apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
        }
        searchView.maxWidth = Int.MAX_VALUE
        searchView.imeOptions.apply {
            EditorInfo.IME_FLAG_NO_EXTRACT_UI
            EditorInfo.IME_ACTION_DONE
        }

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


    /*on Back pressed we checking if search view is expanded then close it first and then close activity*/
    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            searchView.clearFocus()
        } else {
            super.onBackPressed()

        }
    }

}
