package com.muthu.sph.model

import android.os.Parcel
import android.os.Parcelable
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
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readParcelable(Result::class.java.classLoader),
        parcel.readParcelable(Links::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(help)
        parcel.writeString(success)
        parcel.writeParcelable(result, flags)
        parcel.writeParcelable(links, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ListDataModel> {
        override fun createFromParcel(parcel: Parcel): ListDataModel {
            return ListDataModel(parcel)
        }

        override fun newArray(size: Int): Array<ListDataModel?> {
            return arrayOfNulls(size)
        }
    }
}

data class Result(
    @SerializedName("resource_id")
    val resourceId: String?,

    val records: List<Records>?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.createTypedArrayList(Records)
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(resourceId)
        parcel.writeTypedList(records)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Result> {
        override fun createFromParcel(parcel: Parcel): Result {
            return Result(parcel)
        }

        override fun newArray(size: Int): Array<Result?> {
            return arrayOfNulls(size)
        }
    }
}

data class Records(
    @SerializedName("volume_of_mobile_data")
    val volumeOfMobileData: String?,

    val quarter: String?,
    @SerializedName("_id")
    val id: Int?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(volumeOfMobileData)
        parcel.writeString(quarter)
        parcel.writeValue(id)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Records> {
        override fun createFromParcel(parcel: Parcel): Records {
            return Records(parcel)
        }

        override fun newArray(size: Int): Array<Records?> {
            return arrayOfNulls(size)
        }
    }
}

data class Links(
    val start: String?,
    val next: String?,
    val prev: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(start)
        parcel.writeString(next)
        parcel.writeString(prev)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Links> {
        override fun createFromParcel(parcel: Parcel): Links {
            return Links(parcel)
        }

        override fun newArray(size: Int): Array<Links?> {
            return arrayOfNulls(size)
        }
    }
}

