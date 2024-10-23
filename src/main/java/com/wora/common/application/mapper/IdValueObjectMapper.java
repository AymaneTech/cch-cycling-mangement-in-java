package com.wora.common.application.mapper;

import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IdValueObjectMapper {

    default UUID valueObjectToUuid(Object vo) {
        if (vo == null) {
            return null;
        }
        try {
            return (UUID) vo.getClass().getMethod("value").invoke(vo);
        } catch (Exception e) {
            return null;
        }
    }
}
