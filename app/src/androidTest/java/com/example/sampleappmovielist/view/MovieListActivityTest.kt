package com.example.sampleappmovielist.view

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.example.sampleappmovielist.MyApplication
import com.example.sampleappmovielist.R
import com.example.sampleappmovielist.adapter.MovieListAdapter
import com.example.sampleappmovielist.model.Datum
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4ClassRunner::class)
class MovieListActivityTest {


    @get: Rule
    val activityScenario = ActivityScenarioRule(MovieListActivity::class.java)
    val fakeMovieList = arrayListOf<Datum>()
    val recycler_item_position = 1
    val data = prepareData()[recycler_item_position]



    //check search view is displayed
    @Test
    fun test_isSearchViewDisplayed() {
        onView(withId(R.id.search_action)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )

    }

    @Test
    fun test_isSearchViewWorking() {
        onView(withId(R.id.search_action)).perform(ViewActions.click())

    }


    // check progressbar is displayed
    @Test
    fun test_isProgressBarDisplayed() {
        onView(withId(R.id.progress_horizontal)).check(
            matches(
                withEffectiveVisibility(
                    Visibility.VISIBLE
                )
            )
        )
    }

    // check recyclerview is displayed when internet available and progress bar visibility gone
    @Test
    fun test_IsRecyclerviewDisplayed() {
        if (MyApplication.hasNetwork()) {
            onView(withId(R.id.movie_recycler_view)).check(
                matches(
                    withEffectiveVisibility(
                        Visibility.VISIBLE
                    )
                )
            )
        }

    }

    @Test
    fun test_RecyclerviewData() {

        onView(withId(R.id.movie_recycler_view))
            .perform(
                actionOnItemAtPosition<MovieListAdapter.MovieListViewHolder>(
                    recycler_item_position,
                    click()
                )
            )

        onView(withId(R.id.title_tv)).check(matches(withText(data.title)))
    }




    fun prepareData(): ArrayList<Datum> {
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

        fakeMovieList.add(data1)
        fakeMovieList.add(data2)
        fakeMovieList.add(data3)
        return fakeMovieList

    }

}