package com.glofox.classmanager.services;

import java.util.NoSuchElementException;

import com.glofox.classmanager.dtos.BookingDTO;
import com.glofox.classmanager.dtos.CreateBookingRequest;
import com.glofox.classmanager.mappers.BookingMapper;
import com.glofox.classmanager.models.Booking;
import com.glofox.classmanager.models.StudioClass;
import com.glofox.classmanager.repositories.BookingRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class BookingService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingService.class);

    private final BookingRepository bookingRepository;
    private final BookingMapper bookingMapper;
    private final StudioClassService studioClassService;

    public BookingService(
        BookingRepository bookingRepository,
        BookingMapper bookingMapper,
        StudioClassService studioClassService
    ) {
        this.bookingRepository = bookingRepository;
        this.bookingMapper = bookingMapper;
        this.studioClassService = studioClassService;
    }

    public BookingDTO createBooking(CreateBookingRequest bookingToCreate) {
        LOGGER.info("Create booking: {}", bookingToCreate);

        StudioClass studioClass = studioClassService.getByDate(bookingToCreate.date())
                            .orElseThrow(() -> new NoSuchElementException(String.format("No class found at date: %s", bookingToCreate.date().toString())));
        
        Booking booking = new Booking();
        booking.setName(bookingToCreate.name());
        booking.setStudioClass(studioClass);

        bookingRepository.save(booking);
        LOGGER.info("Booking created");
        return bookingMapper.modelToDTO(booking);
    }
}
