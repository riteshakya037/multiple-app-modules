package com.riteshakya.teacher.feature.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import com.riteshakya.businesslogic.repository.school.model.SchoolModel
import com.riteshakya.businesslogic.repository.teacher.model.TeacherModel
import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.main.vm.HomeViewModel
import com.riteshakya.teacher.navigation.Navigator
import com.riteshakya.core.imageloaders.IImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    internal lateinit var homeViewModel: HomeViewModel
    @Inject
    internal lateinit var mainNavigator: Navigator

    @Inject
    lateinit var imageLoader: IImageLoader

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getCurrentUser()
    }

    private fun getCurrentUser() {
        homeViewModel.getCurrentUser()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe {
                    showViewLoading()
                }.doOnSuccess {
                    hideViewLoading()
                    it?.run { handleUser(this) }
                }
                .subscribe()
                .untilStop()
    }

    private fun handleUser(user: BaseUser) {
        when (user) {
            is TeacherModel -> {
                showWelcome(
                        title = "Hey, ${user.firstName}",
                        subtitle = user.schoolModel.schoolName,
                        showBadge = user.isTeacher,
                        badgeText = "Teacher",
                        profileImage = user.profilePicture
                )
            }
            is SchoolModel -> {
                showWelcome(
                        title = "Hey, ${user.nameOfAuthority}",
                        subtitle = user.schoolName,
                        showBadge = false,
                        badgeText = "",
                        profileImage = user.profilePhoto
                )
            }
            else -> {

            }
        }
    }


    private fun showWelcome(
            title: String, subtitle: String, showBadge: Boolean, badgeText: String,
            profileImage: String
    ) {
        welcomeView.title = title
        welcomeView.subtitle = subtitle
        welcomeView.showBadge = showBadge
        welcomeView.badgeText = badgeText
        imageLoader.loadImage(profileImage, welcomeView.image)
    }

    private fun hideViewLoading() {
        welcomeLoading.visibility = GONE
        welcomeView.visibility = VISIBLE
    }

    private fun showViewLoading() {
        welcomeLoading.visibility = VISIBLE
        welcomeView.visibility = INVISIBLE
    }
}