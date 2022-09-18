package com.enestigli.artbookmvvm.view

import com.enestigli.artbookmvvm.R
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.runner.Version.id
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
class ArtFragmentTest {


    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtToArtDetails(){

        val navController = Mockito.mock(NavController::class.java)


        launchFragmentInHiltContainer<ArtFragment>(
            factory = fragmentFactory)
        {
            //hilt ile bu şekilde fragmentları test edicez
            //Bu tarz işlemlerde bir fake bir yapı oluşturmamız lazım , navigation ı test ediceğimiz için navigationcontrollerın kendisini fake etmemiz gerekiyor
            Navigation.setViewNavController(requireView(),navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.fab)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            ArtFragmentDirections.actionArtFragmentToArtDetailsFragment()
        )

    }
}