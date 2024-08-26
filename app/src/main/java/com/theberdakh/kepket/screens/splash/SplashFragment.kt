package com.theberdakh.kepket.screens.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.theberdakh.kepket.R
import com.theberdakh.kepket.common.preferences.SharedPreferencesStorage
import com.theberdakh.kepket.screens.common.extensions.NavigationExtensions.replaceFragment
import com.theberdakh.kepket.screens.login.SignInFragment
import com.theberdakh.kepket.screens.main.MainFragment
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
