package com.riteshakya.ui.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.ui.R


class ViewPagerFragment : BaseFragment() {

    private val navController: NavController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_nav_holder, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inflater = navController.navInflater
        val graph = inflater.inflate(getNavigationGraph())
        graph.startDestination = getNavigationId()
        navController.graph = graph
        navigateToRoot()
    }

    private fun navigateToRoot() {
        try {
            if (navController.currentDestination!!.id != getNavigationId()) {
                val navOptions = NavOptions.Builder()
                        .build()
                navController.navigate(getNavigationId(), null, navOptions)
            }
        } catch (ignored: IllegalStateException) {
        }
    }


    private fun getNavigationId(): Int {
        return if (arguments != null && arguments!!.containsKey(ARGS_ACTION_ID))
            arguments!!.getInt(ARGS_ACTION_ID)
        else throw RuntimeException("Can't find navigation id")
    }

    private fun getNavigationGraph(): Int {
        return if (arguments != null && arguments!!.containsKey(ARGS_NAVIGATION_GRAPH_ID))
            arguments!!.getInt(ARGS_NAVIGATION_GRAPH_ID)
        else throw RuntimeException("Can't find navigation graph")
    }


    companion object {
        private const val ARGS_ACTION_ID: String = "args:action_id"
        private const val ARGS_NAVIGATION_GRAPH_ID: String = "args:navigation_graph_id"

        @JvmStatic
        fun newInstance(navigationGraphId: Int, actionId: Int): Fragment {
            val fragment = ViewPagerFragment()
            val bundle = Bundle()
            bundle.putInt(ARGS_NAVIGATION_GRAPH_ID, navigationGraphId)
            bundle.putInt(ARGS_ACTION_ID, actionId)
            fragment.arguments = bundle
            return fragment
        }
    }

}