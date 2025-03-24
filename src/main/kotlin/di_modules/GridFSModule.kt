package com.smart_conspect.di_modules

import com.smart_conspect.services.GridFSService
import org.koin.dsl.module

val gridFSModule = module {
    single { GridFSService() }
}