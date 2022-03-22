package com.muthu.sph.view

import android.util.Log.d
import androidx.fragment.app.Fragment

private const val TAG = "TRACKING"

abstract class BaseFragment : Fragment() {


    override fun onStart() {
        super.onStart()
        trackScreenView()
        trackItemViewing()
    }

    /**
     * Use tracking the screen changes in this function
     */
    protected open fun trackScreenView() {
        val screenName = getScreenNameForAnalytics()
        d(TAG, "$screenName")
    }

    protected open fun trackItemViewing() {
        val position = itemThatUserCurrentlyViewing()
        d(TAG, "POSITION : $position")
    }

    abstract fun getScreenNameForAnalytics(): String?

    abstract fun itemThatUserCurrentlyViewing(): Int?

}