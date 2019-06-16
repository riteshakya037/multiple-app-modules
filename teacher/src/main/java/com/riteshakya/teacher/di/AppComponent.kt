package com.riteshakya.teacher.di


import com.riteshakya.teacher.TeacherApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AppModule::class,
            AndroidSupportInjectionModule::class,
            ActivityBuilderModule::class,
            NetworkModule::class
        ]
)
interface AppComponent : AndroidInjector<TeacherApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TeacherApp>()
}