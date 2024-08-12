package com.theberdakh.xmlapp.screens.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.xmlapp.R
import com.theberdakh.xmlapp.screens.common.extensions.NavigationExtensions.replaceFragment
import com.theberdakh.xmlapp.core.viewbinding.viewBinding
import com.theberdakh.xmlapp.databinding.LayoutSignUpBinding
import com.theberdakh.xmlapp.screens.main.MainFragment

class SignUpFragment: Fragment(R.layout.layout_sign_up) {
    private val binding: LayoutSignUpBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signUpButtonGoBack.setOnClickListener {
            requireActivity().supportFragmentManager
                .replaceFragment(R.id.activityContainerView, SignInFragment())
        }

        binding.signUpButtonCreateNewAccount.setOnClickListener {
            requireActivity().supportFragmentManager.replaceFragment(
                R.id.activityContainerView,
                MainFragment()
            )
        }

    }

    companion object {
        fun newInstance(): SignUpFragment {
            return SignUpFragment()
        }
    }
}
