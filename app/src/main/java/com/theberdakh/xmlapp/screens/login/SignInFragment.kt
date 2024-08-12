package com.theberdakh.xmlapp.screens.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.xmlapp.R
import com.theberdakh.xmlapp.screens.common.extensions.NavigationExtensions.replaceFragment
import com.theberdakh.xmlapp.core.preferences.SharedPreferencesStorage
import com.theberdakh.xmlapp.core.viewbinding.viewBinding
import com.theberdakh.xmlapp.databinding.LayoutSignInBinding
import com.theberdakh.xmlapp.screens.main.MainFragment

class SignInFragment: Fragment(R.layout.layout_sign_in) {
    private val binding: LayoutSignInBinding by viewBinding()
    private val sharedPreferencesStorage by lazy { SharedPreferencesStorage(requireContext())}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInButtonLogin.setOnClickListener {
           requireActivity().supportFragmentManager.replaceFragment(
               R.id.activityContainerView,
               MainFragment.newInstance()
           )
            sharedPreferencesStorage.isLoggedIn = true
       }

        binding.signInButtonRegister.setOnClickListener {

               requireActivity().supportFragmentManager.beginTransaction()
                    .replace(
                        R.id.activityContainerView,
                        SignUpFragment.newInstance()
                    )
                    .commit()

        }

    }

    companion object {

        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }
}
