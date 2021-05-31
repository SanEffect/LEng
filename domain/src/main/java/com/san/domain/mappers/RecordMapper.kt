package com.san.domain.mappers

import com.san.domain.entities.RecordEntity
import com.san.domain.models.RecordDto
import org.mapstruct.Mapper

@Mapper
interface RecordMapper {
    fun toDto(record: RecordEntity) : RecordDto
    fun toEntity(record: RecordDto) : RecordEntity
}