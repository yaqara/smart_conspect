package com.smart_conspect.di_modules

import com.smart_conspect.data.repositories.AssignmentRepository
import com.smart_conspect.data.repositories.ClassRepository
import com.smart_conspect.data.repositories.UserRepository
import org.koin.dsl.module

val repositoriesModule = module {
    single { UserRepository }
    single { AssignmentRepository }
    single { ClassRepository }
}