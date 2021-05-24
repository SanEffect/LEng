package com.san.domain.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
)