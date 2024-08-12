package com.theberdakh.xmlapp.screens.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.theberdakh.xmlapp.R
import com.theberdakh.xmlapp.screens.common.extensions.NavigationExtensions.replaceFragment
import com.theberdakh.xmlapp.core.preferences.SharedPreferencesStorage
import com.theberdakh.xmlapp.screens.login.SignInFragment
import com.theberdakh.xmlapp.screens.main.MainFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.layout_splash) {
    private val sharedPreferencesStorage by lazy { SharedPreferencesStorage(requireContext()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragment: Fragment = if (sharedPreferencesStorage.isLoggedIn) {
            MainFragment.newInstance()
        } else {
            SignInFragment.newInstance()
        }


        lifecycleScope.launch {
            delay(SPLASH_SCREEN_DURATION)

            requireActivity().supportFragmentManager.replaceFragment(
                R.id.activityContainerView,
                fragment
            )

        }
    }


    companion object {
        /**
         * Splash Screen Duration 1.5 second
         */
        private const val SPLASH_SCREEN_DURATION = 1500L

        fun newInstance(): SplashFragment {
            return SplashFragment()
        }
    }

}
