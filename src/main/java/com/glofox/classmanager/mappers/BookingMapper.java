package com.glofox.classmanager.mappers;

import java.time.LocalDate;

import com.glofox.classmanager.dtos.BookingDTO;
import com.glofox.classmanager.models.Booking;
import com.glofox.classmanager.models.StudioClass;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookingMapper {
    @Mapping(source = "studioClass", target = "date", qualifiedByName = "getBookingDate")
    BookingDTO modelToDTO(Booking booking);

    @Named("getBookingDate")
    public static LocalDate getBookingDate(StudioClass studioClass) {
        return studioClass.getDate();
    }
}
