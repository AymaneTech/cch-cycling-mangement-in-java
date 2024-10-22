package com.wora.common.application.mapper;

import org.mapstruct.MapperConfig;

@MapperConfig(componentModel = "spring")
public interface BaseMapper<Entity, Request, Response> {
    Entity toEntity(Request dto);

    Response toResponseDto(Entity entity);
}
