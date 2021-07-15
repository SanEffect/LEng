package com.san.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.pozo.KotlinBuilder
import com.san.domain.dataTransferObjects.RecordDto
import java.util.*

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

    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
) {
    companion object {
        fun toEntity(record: RecordDto): RecordEntity {
            return RecordEntity(
                record.title,
                record.description,
                record.creationDate,
                record.isDeleted,
                record.id,
            )
        }
    }

    fun toDto(): RecordDto {
        return RecordDto(title, description, id, isDeleted, creationDate)
    }
}
