package com.san.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.pozo.KotlinBuilder
import com.san.domain.models.RecordDto

@KotlinBuilder
@Entity(tableName = "records")
data class RecordEntity(

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "text")
    var description: String = "",

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,
    //var id: String = UUID.randomUUID().toString(),

    @ColumnInfo(name = "is_deleted")
    var isDeleted: Boolean = false,

    @ColumnInfo(name = "creation_date")
    var creationDate: Long = System.currentTimeMillis()
) {
    companion object {
        fun toEntity(record: RecordDto): RecordEntity {
            return RecordEntity(
                record.title,
                record.description,
                record.id,
                record.isDeleted,
                record.creationDate)
        }
    }

    fun toDto(): RecordDto {
        return RecordDto(title, description, id, isDeleted, creationDate)
    }
}