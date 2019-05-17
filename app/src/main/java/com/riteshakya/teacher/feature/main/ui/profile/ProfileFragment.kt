package com.riteshakya.teacher.feature.main.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.main.vm.ProfileViewModel
import com.riteshakya.teacher.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    @Inject
    internal lateinit var profileViewModel: ProfileViewModel
    @Inject
    internal lateinit var mainNavigator: Navigator

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutBtn.setOnClickListener {
            logoutUser()
        }
    }

    private fun logoutUser() {
        profileViewModel.logout()
                .addLoading()
                .subscribe {
                    mainNavigator.showMain(context!!)
                    activity?.finishAffinity()
                }.untilStop()
    }
}