package com.bangkit.moviecatalog.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.bangkit.moviecatalog.R
import com.bangkit.moviecatalog.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class MainActivityTest {

    private val defaultListSize = 20

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Instrumental Testing Documentation:
     * there are six testing:
     * - display movie list     :
     *          - Checking tab_movie is displayed
     *          - Perform Click on tab_movie result opening section with rv_list_item
     *          - Checking rv_list_item are rendered on tab movie
     *
     * - display tv show list   :
     *          - Checking tab_tv is displayed
     *          - Perform Click on tab_tv result opening section with rv_list_item
     *          - Checking rv_list_item are rendered on tab tv show
     *
     * - see detail of movie    :
     *          - Perform opening section movie by click on tab_movie
     *          - Perform Click on first item of rv_list_item  will result on opening detail activity
     *          - Checking movie_poster are displayed
     *          - Checking movie_title are displayed
     *          - Checking movie_desc are displayed
     *          - Checking movie_rating are displayed
     *          - Checking set_favorite_menu are displayed
     *          - Perform back to previous activity
     *
     * - see detail of tv show  :
     *          - Perform opening section tv show by click on tab_tv
     *          - Perform Click on first item of rv_list_item  will result on opening detail activity
     *          - Checking movie_poster are displayed
     *          - Checking movie_title are displayed
     *          - Checking movie_desc are displayed
     *          - Checking movie_rating are displayed
     *          - Checking set_favorite_menu are displayed
     *          - Perform back to previous activity
     *
     * - display favorite movie list:
     *          - Perform opening section movie by click on tab_movie
     *          - Perform Click on third item of rv_list_item  will result on opening detail activity
     *          - Checking set_favorite_menu are displayed
     *          - Perform Click on set_favorite_menu to add movie into favorite
     *          - Perform back to home page
     *          - Perform opening favorite page by click on favorite_page
     *          - Checking tab_movie is displayed
     *          - Perform Click on tab_movie result opening section with rv_list_item
     *          - Checking rv_list_item are rendered with favorite movies on tab movie
     *
     * - display favorite tv show list:
     *          - Perform opening section movie by click on tab_tv
     *          - Perform Click on third item of rv_list_item  will result on opening detail activity
     *          - Checking set_favorite_menu are displayed
     *          - Perform Click on set_favorite_menu to add tv show into favorite
     *          - Perform back to home page
     *          - Perform opening favorite page by click on favorite_page
     *          - Checking tab_tv is displayed
     *          - Perform Click on tab_tv result opening section with rv_list_item
     *          - Checking rv_list_item are rendered with favorite tv shows on tab tv
     *
     * */

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.getEspressoIdlingResource())
    }

    @Test
    fun displayMovieList() {
        onView(withId(R.string.tab_movie_id)).check(matches(isDisplayed()))
        onView(withId(R.string.tab_movie_id)).perform(click())
        onView(withId(R.id.rv_list_item)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(defaultListSize))
    }

    @Test
    fun displayTvShowList() {
        onView(withId(R.string.tab_tv_id)).check(matches(isDisplayed()))
        onView(withId(R.string.tab_tv_id)).perform(click())
        onView(withId(R.id.rv_list_item)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(defaultListSize))
    }

    @Test
    fun openMovieDetail() {
        onView(withId(R.string.tab_movie_id)).perform(click())
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.movie_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.set_favorite_menu)).check(matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun openTvDetail() {
        onView(withId(R.string.tab_tv_id)).perform(click())
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(R.id.movie_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_desc)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.set_favorite_menu)).check(matches(isDisplayed()))
        onView(isRoot()).perform(pressBack())
    }

    @Test
    fun displayFavoriteMovieList() {
        onView(withId(R.string.tab_movie_id)).perform(click())
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.set_favorite_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.set_favorite_menu)).perform(click())
        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.favorite_page)).perform(click())
        onView(withId(R.string.tab_movie_id)).check(matches(isDisplayed()))
        onView(withId(R.string.tab_movie_id)).perform(click())
        onView(withId(R.id.rv_list_item)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(defaultListSize))
    }

    @Test
    fun displayFavoriteTvShowList() {
        onView(withId(R.string.tab_tv_id)).perform(click())
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
        onView(withId(R.id.set_favorite_menu)).check(matches(isDisplayed()))
        onView(withId(R.id.set_favorite_menu)).perform(click())
        onView(isRoot()).perform(pressBack())

        onView(withId(R.id.favorite_page)).perform(click())
        onView(withId(R.string.tab_tv_id)).check(matches(isDisplayed()))
        onView(withId(R.string.tab_tv_id)).perform(click())
        onView(withId(R.id.rv_list_item)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_list_item)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(defaultListSize))
    }

}