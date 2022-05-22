package com.atakanmadanoglu.experiencesapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(
    tableName = "pictures_table",
    foreignKeys = [
        ForeignKey(entity = Experience::class, parentColumns = ["id"], childColumns = ["experience_id"])
    ]
)
data class Picture(
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    @ColumnInfo(name = "experience_id")
    val experienceID: String,
    @ColumnInfo(name = "picture_byte_array")
    val pictureByteArray: ByteArray? = null,
    @ColumnInfo(name = "added_at")
    val addedAt: Timestamp? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Picture

        if (id != other.id) return false
        if (!pictureByteArray.contentEquals(other.pictureByteArray)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + pictureByteArray.contentHashCode()
        return result
    }
}
