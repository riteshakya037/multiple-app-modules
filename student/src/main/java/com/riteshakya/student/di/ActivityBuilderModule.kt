package com.riteshakya.student.di

import com.riteshakya.student.feature.login.di.LoginActivityModule
import com.riteshakya.student.feature.main.di.MainActivityModule
import com.riteshakya.student.navigation.RouteActivityModule
import dagger.Module

@Module(includes = [RouteActivityModule::class, MainActivityModule::class, LoginActivityModule::class])
abstract class ActivityBuilderModule