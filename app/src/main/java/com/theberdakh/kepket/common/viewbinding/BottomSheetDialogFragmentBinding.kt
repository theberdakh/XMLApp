package com.theberdakh.kepket.common.viewbinding

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty


inline fun <reified T : ViewBinding> BottomSheetDialogFragment.viewBinding() = BottomSheetViewBindingDelegate(T::class.java)

class BottomSheetViewBindingDelegate<T : ViewBinding>(private val bindingClass: Class<T>) :
    ReadOnlyProperty<BottomSheetDialogFragment, T> {
    /**
     * initiate variable for binding view
     */
    private var binding: T? = null

    @Suppress("UNCHECKED_CAST")
    override fun getValue(thisRef: BottomSheetDialogFragment, property: KProperty<*>): T {
        binding?.let { return it }

        /**
         * inflate View class
         */
        val inflateMethod = bindingClass.getMethod("inflate", LayoutInflater::class.java)

        /**
         * Bind layout
         */
        val invokeLayout = inflateMethod.invoke(null, LayoutInflater.from(thisRef.context)) as T

        /**
         * Return binding layout
         */
        return invokeLayout.also { this.binding = it }
    }
}
