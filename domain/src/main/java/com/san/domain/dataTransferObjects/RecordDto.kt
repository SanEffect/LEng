package com.san.domain.dataTransferObjects

import android.os.Parcelable
import com.github.pozo.KotlinBuilder
import com.san.domain.entities.RecordEntity
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class RecordDto(
    var title: String = "",
    var description: String = "",
    var id: String = UUID.randomUUID().toString(),
    var isDeleted: Boolean = false,
    var creationDate: Long = System.currentTimeMillis()
) : Parcelable {
    companion object {
        fun toDto(record: RecordEntity): RecordDto {
            return RecordDto(
                record.title,
                record.description,
                record.id,
                record.isDeleted,
                record.creationDate
            )
        }
    }

    fun toEntity(): RecordEntity {
        return RecordEntity(title, description, id, isDeleted, creationDate)
    }
}
