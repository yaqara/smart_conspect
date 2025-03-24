package com.smart_conspect.plugins

import com.smart_conspect.di_modules.authenticationModule
import com.smart_conspect.di_modules.databaseModule
import com.smart_conspect.di_modules.gridFSModule
import com.smart_conspect.di_modules.repositoriesModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()
        modules(databaseModule, authenticationModule, gridFSModule, repositoriesModule)
    }
}