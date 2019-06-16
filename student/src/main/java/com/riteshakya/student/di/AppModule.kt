package com.riteshakya.student.di

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.riteshakya.businesslogic.data.datasource.auth.AuthModule
import com.riteshakya.businesslogic.data.datasource.image.ImageModule
import com.riteshakya.businesslogic.data.datasource.school.SchoolModule
import com.riteshakya.businesslogic.data.datasource.student.StudentModule
import com.riteshakya.businesslogic.data.datasource.teacher.TeacherModule
import com.riteshakya.businesslogic.data.datasource.user.UserModule
import com.riteshakya.core.di.AppViewModelFactory
import com.riteshakya.student.StudentApp
import com.riteshakya.ui.imageloaders.GlideLoader
import com.riteshakya.ui.imageloaders.IImageLoader
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Provider
import javax.inject.Singleton

@Module(includes = [AppModule.Supporting::class, AppModule.Repository::class])
abstract class AppModule {
    @Binds
    abstract fun bindApplication(app: StudentApp): Application

    @Module
    class Supporting {
        @Provides
        @Singleton
        fun provideContext(application: Application): Context = application

        @Provides
        @Singleton
        fun provideImageLoader(glideLoader: GlideLoader): IImageLoader = glideLoader

        @Provides
        fun provideViewModelFactory(
            providers: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory =
            AppViewModelFactory(providers)
    }

    @Module(
        includes = [
            SchoolModule.Repositories::class,
            UserModule.Repositories::class,
            TeacherModule.Repositories::class,
            StudentModule.Repositories::class,
            ImageModule.Repositories::class,
            AuthModule.Repositories::class
        ]
    )
    class Repository
}