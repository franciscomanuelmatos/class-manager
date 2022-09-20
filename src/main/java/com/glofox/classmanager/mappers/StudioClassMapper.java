package com.glofox.classmanager.mappers;

import com.glofox.classmanager.dtos.StudioClassDTO;
import com.glofox.classmanager.models.StudioClass;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StudioClassMapper {
    StudioClassDTO modelToDTO(StudioClass studioClass);
}
