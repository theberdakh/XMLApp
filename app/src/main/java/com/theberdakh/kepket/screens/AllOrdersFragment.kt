package com.theberdakh.kepket.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.kepket.R
import com.theberdakh.kepket.common.viewbinding.viewBinding
import com.theberdakh.kepket.databinding.AllOrdersFragmentBinding
import com.theberdakh.kepket.screens.common.extensions.NavigationExtensions.replaceFragment

class AllOrdersFragment : Fragment(R.layout.all_orders_fragment) {
    private val binding by viewBinding<AllOrdersFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.allOrdersFab.setOnClickListener {
            requireActivity().supportFragmentManager.replaceFragment(R.id.activityContainerView, AllFoodsFragment.newInstance())

        }

    }

    companion object {
        fun newInstance(): AllOrdersFragment {
            return AllOrdersFragment()
        }
    }
}
