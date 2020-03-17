package com.example.sampleappmovielist.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.sampleappmovielist.MyApplication
import com.example.sampleappmovielist.R
import com.example.sampleappmovielist.adapter.MovieListAdapter
import com.example.sampleappmovielist.model.Datum
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MovieListActivityTest {


    @get: Rule
    val activityScenario = ActivityScenarioRule(MovieListActivity::class.java)
    val movieList = arrayListOf<Datum>()

    // check progressbar is displayed
    @Test
    fun test_isProgressBarDisplayed() {
        onView(withId(R.id.progress_horizontal)).check(
            matches(
                ViewMatchers.withEffectiveVisibility(
                    ViewMatchers.Visibility.VISIBLE
                )
            )
        )
    }

    // check recyclerview is displayed when internet available
    @Test
    fun test_IsRecyclerviewDisplayed() {
        if (MyApplication.hasNetwork()) {
            onView(withId(R.id.movie_recycler_view)).check(
                matches(
                    ViewMatchers.withEffectiveVisibility(
                        ViewMatchers.Visibility.VISIBLE
                    )
                )
            )
        }
    }

    @Test
    fun test_IsRecyclerviewAddingItem() {

        onView(withId(R.id.movie_recycler_view))

    }


    fun prepareData() {
        val data1 = Datum().apply {
            this.genre = "genre_one"
            this.id = 1
            this.title = "title_one"
            this.year = "2001"
            this.poster =
                "https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg"
        }
        val data2 = Datum().apply {
            this.genre = "genre_two"
            this.id = 1
            this.title = "title_two"
            this.year = "2002"
            this.poster =
                "https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg"
        }
        val data3 = Datum().apply {
            this.genre = "genre_three"
            this.id = 1
            this.title = "title_three"
            this.year = "2003"
            this.poster =
                "https://image.tmdb.org/t/p/w370_and_h556_bestv2/bXrZ5iHBEjH7WMidbUDQ0U2xbmr.jpg"
        }

        movieList.add(data1)
        movieList.add(data2)
        movieList.add(data3)

    }

}