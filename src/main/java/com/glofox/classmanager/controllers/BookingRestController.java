package com.glofox.classmanager.controllers;

import com.glofox.classmanager.dtos.BookingDTO;
import com.glofox.classmanager.dtos.CreateBookingRequest;
import com.glofox.classmanager.services.BookingService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/bookings")
public class BookingRestController {
    private final BookingService bookingService;

    public BookingRestController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping
    public BookingDTO createBooking(@RequestBody @Valid CreateBookingRequest bookingToCreate) {
        return bookingService.createBooking(bookingToCreate);
    }
}
