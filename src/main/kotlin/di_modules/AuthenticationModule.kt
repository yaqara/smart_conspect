package com.smart_conspect.di_modules

import com.smart_conspect.data.repositories.UserRepository
import com.smart_conspect.services.AuthService
import org.koin.dsl.module

val authenticationModule = module {
    single { UserRepository }
    single { AuthService(get()) }
}