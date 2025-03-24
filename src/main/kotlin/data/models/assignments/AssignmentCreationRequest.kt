package com.smart_conspect.data.models.assignments

import kotlinx.serialization.Serializable

@Serializable
data class AssignmentCreationRequest(
    val classId: String,
    val title: String,
    val description: String,
    val deadline: Long
)
