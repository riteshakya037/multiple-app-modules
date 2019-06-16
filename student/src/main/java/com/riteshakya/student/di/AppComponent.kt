package com.riteshakya.student.di


import com.riteshakya.student.StudentApp
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AppModule::class,
            AndroidSupportInjectionModule::class,
            ActivityBuilderModule::class
        ]
)
interface AppComponent : AndroidInjector<StudentApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<StudentApp>()
}