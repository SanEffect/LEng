package com.san.domain.mappers

import com.san.domain.dataTransferObjects.RecordDto
import com.san.domain.entities.RecordEntity
import org.mapstruct.Mapper

@Mapper
interface RecordMapper {
    fun toDto(record: RecordEntity): RecordDto
    fun toEntity(record: RecordDto): RecordEntity
}
