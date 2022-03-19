package com.muthu.sph.model

/**
 * data class to hold the error messages
 * wherever we can use to pass the custom
 * messages with @param [message]
 * with @param [isError]
 */
data class SphError(
    val isError: Boolean,
    val message: String
)
