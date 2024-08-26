package com.theberdakh.kepket.screens.login

import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.kepket.R
import com.theberdakh.kepket.databinding.LayoutSignInBinding
import com.theberdakh.kepket.common.preferences.SharedPreferencesStorage
import com.theberdakh.kepket.common.viewbinding.viewBinding
import com.theberdakh.kepket.screens.common.extensions.NavigationExtensions.replaceFragment
import com.theberdakh.kepket.screens.common.extensions.ViewExtensions.showBorder
import com.theberdakh.kepket.screens.main.MainFragment

class SignInFragment : Fragment(R.layout.layout_sign_in) {
    private val binding: LayoutSignInBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signInLoginBtn.setOnClickListener {
            requireActivity().supportFragmentManager.replaceFragment(R.id.activityContainerView, MainFragment.newInstance())
        }

    }

    companion object {

        fun newInstance(): SignInFragment {
            return SignInFragment()
        }
    }
}
