package com.riteshakya.teacher.feature.login.ui.signup.logoupload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.riteshakya.core.image.PhotoFragment
import com.riteshakya.teacher.BuildConfig
import com.riteshakya.teacher.R
import com.riteshakya.teacher.feature.login.navigation.LoginNavigator
import com.riteshakya.teacher.feature.login.vm.SignUpViewModel
import com.riteshakya.teacher.navigation.Navigator
import com.riteshakya.ui.components.MenuBottomSheet
import com.riteshakya.core.imageloaders.IImageLoader
import kotlinx.android.synthetic.main.fragment_logo_upload.*
import javax.inject.Inject

class LogoUploadFragment : PhotoFragment() {

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
    ): View = inflater.inflate(R.layout.fragment_logo_upload, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeClickListeners()
        initializeModeChanges()
        initializeMediaPicker()
        imageLoader.loadImage(signUpViewModel.schoolLogo.value ?: "", imageView)
    }


    private fun initializeClickListeners() {
        finishBtn.setOnClickListener {
            signUpUser()
        }
        addImgBtn.setOnClickListener {
            showPhotoOptions()
        }
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

    private fun initializeModeChanges() {
        signUpViewModel.schoolLogo.observe(this, Observer {
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

    override fun setUpImage(currentPhotoPath: String) {
        signUpViewModel.schoolLogo.value = currentPhotoPath
        imageLoader.loadImage(currentPhotoPath, imageView)
    }

    private fun showPhotoOptions() {
        mediaPickerBottomSheet.show(fragmentManager!!)
    }
}