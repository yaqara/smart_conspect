package com.smart_conspect

import com.smart_conspect.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configurePostgresql()
    configureMongoDB()
    configureContentNegotiation()
    configureJWT()
    configureRouting()
}
