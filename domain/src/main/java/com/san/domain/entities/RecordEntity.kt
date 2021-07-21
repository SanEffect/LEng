package com.san.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.pozo.KotlinBuilder
import com.san.domain.dataTransferObjects.RecordDto
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
@Entity(tableName = "records")
data class RecordEntity(

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "text")
    var description: String = "",

    @ColumnInfo(name = "creation_date")
    var creationDate: Long = System.currentTimeMillis(),

    @ColumnInfo(name = "is_deleted")
    var isDeleted: Boolean = false,

    @ColumnInfo(name = "is_draft")
    var isDraft: Boolean = false,

    @ColumnInfo(name = "background_color")
    var backgroundColor: String? = null,

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
) : Parcelable
/*{
    companion object {
        fun toEntity(record: RecordDto): RecordEntity {
            return RecordEntity(
                record.title,
                record.description,
                record.creationDate,
                record.isDeleted,
                record.,
                record.isDeleted,
                record.id,
            )
        }
    }

    fun toDto(): RecordDto {
        return RecordDto(title, description, id, isDeleted, creationDate)
    }
}*/
