package com.enestigli.artbookmvvm.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.enestigli.artbookmvvm.repository.FakeArtRepositoryTest
import com.enestigli.artbookmvvm.viewmodel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject
import com.enestigli.artbookmvvm.R
import com.enestigli.artbookmvvm.adapter.ImageRecyclerAdapter
import com.enestigli.artbookmvvm.getOrAwaitValue
import com.google.common.truth.Truth.assertThat


@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ImageApiFragmentTest {


    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun selectImage(){

        val selectedImageUrl = "enes.com"
        val navController = Mockito.mock(NavController::class.java)
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ImageApıFragment>(
            factory = fragmentFactory
        ){
            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewModel
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(0,click())

        )

        Mockito.verify(navController).popBackStack()
        assertThat(testViewModel.selectedImageURL.getOrAwaitValue()).isEqualTo(selectedImageUrl)

    }

}