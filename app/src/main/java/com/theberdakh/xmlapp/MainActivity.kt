package com.theberdakh.xmlapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.theberdakh.xmlapp.screens.common.extensions.NavigationExtensions.addFragment
import com.theberdakh.xmlapp.screens.splash.SplashFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container_main_activity)

        supportFragmentManager.addFragment(
            R.id.activityContainerView,
            SplashFragment.newInstance(),
            SplashFragment.Companion::class.java.simpleName
        )

    }
}
