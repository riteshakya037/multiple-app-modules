package com.riteshakya.student.feature.main.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import com.riteshakya.businesslogic.repository.student.model.StudentModel
import com.riteshakya.core.model.BaseUser
import com.riteshakya.core.platform.BaseFragment
import com.riteshakya.student.R
import com.riteshakya.student.feature.main.vm.HomeViewModel
import com.riteshakya.core.imageloaders.IImageLoader
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject

class HomeFragment : BaseFragment() {

    @Inject
    internal lateinit var homeViewModel: HomeViewModel

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
            is StudentModel -> {
                showWelcome(
                    title = "Hey, ${user.firstName}",
                    subtitle = user.schoolModel.schoolName,
                    showBadge = true,
                    badgeText = "${user.className} - ${user.section}",
                    profileImage = user.profilePicture
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
        welcomeView.badgeIsLight = true
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