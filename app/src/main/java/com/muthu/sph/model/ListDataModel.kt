package com.muthu.sph.model

import com.google.gson.annotations.SerializedName

/**
 * Data class [ListDataModel]
 * the response from the @see baseUrl
 */
data class ListDataModel(
    val help: String?,
    val success: String?,
    val result: Result?,
    @SerializedName("_links")
    val links: Links?,
)

data class Result(
    @SerializedName("resource_id")
    val resourceId: String?,

    val records: List<Records>?
)

data class Records(
    @SerializedName("volume_of_mobile_data")
    val volumeOfMobileData: String?,

    val quarter: String?,
    @SerializedName("_id")
    val id: Int?
)

data class Links(
    val start: String?,
    val next: String?,
    val prev: String?
)

