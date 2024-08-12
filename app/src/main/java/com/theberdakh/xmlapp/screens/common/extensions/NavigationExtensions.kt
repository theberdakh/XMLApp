package com.theberdakh.xmlapp.screens.common.extensions

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.theberdakh.xmlapp.R

object NavigationExtensions {

    fun FragmentManager.replaceFragment(
        @IdRes navHostViewId: Int,
        newFragment: Fragment,
        tag: String = newFragment::class.java.simpleName,
        @AnimRes @AnimatorRes enterAnim: Int = R.anim.alpha_in,
        @AnimRes @AnimatorRes exitAnim: Int = R.anim.alpha_out,
        @AnimRes @AnimatorRes popEnterAnim: Int = R.anim.popup_in,
        @AnimRes @AnimatorRes popExitAnim: Int = R.anim.popup_out
    ) {
        this.beginTransaction()
            .replace(navHostViewId, newFragment, tag)
            .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
            .commit()
    }

    fun FragmentManager.addFragment(
        @IdRes navHostViewId: Int,
        newFragment: Fragment,
        tag: String = newFragment::class.java.simpleName,
        @AnimRes @AnimatorRes enterAnim: Int = R.anim.alpha_in,
        @AnimRes @AnimatorRes exitAnim: Int = R.anim.alpha_out,
        @AnimRes @AnimatorRes popEnterAnim: Int = R.anim.popup_in,
        @AnimRes @AnimatorRes popExitAnim: Int = R.anim.popup_out) {
        this.beginTransaction()
            .add(navHostViewId, newFragment, tag)
            .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
            .commit()
    }


    fun FragmentManager.addFragmentToBackStack(
        @IdRes fragmentContainerId: Int,
        fragment: Fragment,
        tag: String = fragment::class.java.simpleName,
        @AnimRes @AnimatorRes enterAnim: Int = R.anim.alpha_in,
        @AnimRes @AnimatorRes exitAnim: Int = R.anim.alpha_out,
        @AnimRes @AnimatorRes popEnterAnim: Int = R.anim.popup_in,
        @AnimRes @AnimatorRes popExitAnim: Int = R.anim.popup_out
    ) {
        val fragmentPopped = popBackStackImmediate(tag, 0)
        if (!fragmentPopped && findFragmentByTag(tag) == null) {
            beginTransaction()
                .setReorderingAllowed(true) // Optimize state changes
                .replace(fragmentContainerId, fragment, tag) // Use replace instead of add
                .setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
                .addToBackStack(tag)
                .commit()
        }
    }

}
