package com.riteshakya.teacher.di

import com.riteshakya.teacher.feature.login.di.LoginActivityModule
import com.riteshakya.teacher.navigation.RouteActivityModule
import dagger.Module

@Module(includes = [LoginActivityModule::class, RouteActivityModule::class])
abstract class ActivityBuilderModule