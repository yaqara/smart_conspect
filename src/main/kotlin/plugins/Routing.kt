package com.smart_conspect.plugins

import com.smart_conspect.routing.assignmentsRoutes
import com.smart_conspect.routing.authenticationRoutes
import com.smart_conspect.routing.gridFSRoutes
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        authenticationRoutes()
        gridFSRoutes()
        assignmentsRoutes()
    }
}