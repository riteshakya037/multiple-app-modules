package com.riteshakya.student.feature.login.ui.signup.profilepicture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.riteshakya.core.image.PhotoFragment
import com.riteshakya.student.BuildConfig
import com.riteshakya.student.R
import com.riteshakya.student.feature.login.navigation.LoginNavigator
import com.riteshakya.student.feature.login.vm.SignUpViewModel
import com.riteshakya.student.navigation.Navigator
import com.riteshakya.ui.components.MenuBottomSheet
import com.riteshakya.ui.imageloaders.IImageLoader
import kotlinx.android.synthetic.main.fragment_profile_picture.*
import javax.inject.Inject

class ProfilePictureFragment : PhotoFragment() {

    @Inject
    internal lateinit var signUpViewModel: SignUpViewModel
    @Inject
    lateinit var imageLoader: IImageLoader
    @Inject
    lateinit var navigator: LoginNavigator
    @Inject
    internal lateinit var mainNavigator: Navigator

    private lateinit var mediaPickerBottomSheet: MenuBottomSheet

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile_picture, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeClickListeners()
        initializeModeChanges()
        initializeMediaPicker()
        imageLoader.loadImage(signUpViewModel.profilePhoto.value ?: "", imageView)
    }

    private fun initializeClickListeners() {
        addImgBtn.setOnClickListener {
            showPhotoOptions()
        }
        finishBtn.setOnClickListener {
            signUpUser()
        }
    }

    private fun initializeModeChanges() {
        signUpViewModel.profilePhoto.observe(this, Observer {
            if (it.isNullOrBlank()) {
                addImgBtn.visibility = VISIBLE
                imageView.visibility = GONE
                finishBtn.isEnabled = false
            } else {
                addImgBtn.visibility = GONE
                imageView.visibility = VISIBLE
                finishBtn.isEnabled = true
            }
        })
    }

    private fun initializeMediaPicker() {
        mediaPickerBottomSheet = MenuBottomSheet.Builder(context!!)
                .setOnMenuSelectedListener(object : MenuBottomSheet.MenuSelectedListener {
                    override fun onDismiss() {
                    }

                    override fun onMenuSelected(id: Int) {
                        when (id) {
                            R.id.chooseAction -> {
                                photoPickHelper.requestPickPhoto()
                            }
                            R.id.takeAction -> {
                                photoPickHelper.requestTakePhoto(BuildConfig.APPLICATION_ID)
                            }
                        }
                    }
                })
                .setTitle("Select")
                .setMenuItems(R.menu.photo_action_menu)
                .create()
    }

    private fun signUpUser() {
        signUpViewModel.signUpUser()
                .addLoading()
                .subscribe({
                    mainNavigator.showMain(context!!)
                    activity?.finishAffinity()
                }, {})
                .untilStop()
    }

    override fun setUpImage(currentPhotoPath: String) {
        signUpViewModel.profilePhoto.value = currentPhotoPath
        imageLoader.loadImage(currentPhotoPath, imageView)
    }

    private fun showPhotoOptions() {
        mediaPickerBottomSheet.show(fragmentManager!!)
    }
}