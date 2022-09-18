package com.enestigli.artbookmvvm.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.enestigli.artbookmvvm.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint // Hilt ile çalışırken nerden uygulamanın başlayacağını söylememiz lazım bunu eklememiz lazım eklemezsek sorun çıkacaktır
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}