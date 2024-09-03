package com.theberdakh.kepket.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.theberdakh.kepket.R
import com.theberdakh.kepket.common.viewbinding.viewBinding
import com.theberdakh.kepket.databinding.AllFoodsFragmentBinding

class AllFoodsFragment: Fragment(R.layout.all_foods_fragment) {
    private val binding by viewBinding<AllFoodsFragmentBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



    }

    companion object {
        fun newInstance(): AllFoodsFragment {
            return AllFoodsFragment()
        }
    }
}
