package com.saha.lifeplustenicaltest.data.model

data class GenericResponse<T : Any?> (
    val error: Boolean = true,
    val message: String = "",
    val data: T? = null,
)

