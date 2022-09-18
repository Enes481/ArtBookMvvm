package com.enestigli.artbookmvvm.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.enestigli.artbookmvvm.R
import com.enestigli.artbookmvvm.getOrAwaitValue
import com.enestigli.artbookmvvm.model.Art
import com.enestigli.artbookmvvm.repository.FakeArtRepositoryTest
import com.enestigli.artbookmvvm.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat

@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory


    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsToImageApi(){

        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory)
        {
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(click())

        Mockito.verify(navController).navigate(
            ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApıFragment()
        )
    }

    @Test
    fun testOnBackPressed(){
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory)
        {
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.pressBack()
        Mockito.verify(navController).popBackStack() // geriye batık geriye gittimi kontrol ettik


    }

    @Test
    fun testSave(){

        val testViewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ArtDetailsFragment>(
            factory = fragmentFactory
        ){
            viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.nameText)).perform(replaceText("mona lisa"))
        Espresso.onView(withId(R.id.artistText)).perform(replaceText("da vinci"))
        Espresso.onView(withId(R.id.yearText)).perform(replaceText("1500"))
        Espresso.onView(withId(R.id.saveButton)).perform(click())

        assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art(
                "mona lisa",
                "da vinci",
                1500,
                "")
        )


    }


}