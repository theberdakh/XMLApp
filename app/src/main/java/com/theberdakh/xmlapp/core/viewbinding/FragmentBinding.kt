package com.theberdakh.xmlapp.core.viewbinding

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding() = FragmentViewBindingDelegate(T::class.java, this)

class FragmentViewBindingDelegate<T : ViewBinding>(
    bindingClass: Class<T>,
    private val fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {


    /**
     * initiate variable for binding view
     */
    private var binding: T? = null


    /**
     * get the bind method from View class
     */
    private val bindMethod = bindingClass.getMethod("bind", View::class.java)


    /**
     * Adding observer to the fragment lifecycle
     */
    init {
        fragment.lifecycle.addObserver(object : DefaultLifecycleObserver {
            val viewLifecycleOwnerObserver = Observer<LifecycleOwner?> { owner ->
                if (owner == null) {
                    binding = null
                }
            }
            override fun onCreate(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.observeForever(viewLifecycleOwnerObserver)
            }

            override fun onDestroy(owner: LifecycleOwner) {
                fragment.viewLifecycleOwnerLiveData.removeObserver(viewLifecycleOwnerObserver)
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {

        binding?.let {
            if(it.root === thisRef.view) {
                return it
            }
        }


        /**
         * Checking the fragment's view
         */
        val view = thisRef.view ?: error("Should not attempt to get bindings when the Fragment's view is null.")


        /**
         * Bind layout
         */
        val invoke = bindMethod.invoke(null, view) as T

        return invoke.also { this.binding = it }
    }
}
