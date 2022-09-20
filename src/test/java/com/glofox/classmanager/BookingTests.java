package com.glofox.classmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.glofox.classmanager.dtos.BookingDTO;
import com.glofox.classmanager.dtos.CreateBookingRequest;
import com.glofox.classmanager.models.StudioClass;
import com.glofox.classmanager.repositories.StudioClassRepository;
import com.glofox.classmanager.services.BookingService;
import com.glofox.classmanager.services.StudioClassService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class BookingTests {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private StudioClassRepository studioClassRepository;

    @MockBean
    private StudioClassService studioClassService;

    @Test
    public void createBookingWhenClassAvailable() {
        // GIVEN
        LocalDate now = LocalDate.now();
        CreateBookingRequest request = new CreateBookingRequest("test user", now);
        StudioClass studioClass = createStudioClass(now);

        when(studioClassService.getByDate(any())).thenReturn(Optional.of(studioClass));

        // WHEN
        BookingDTO createdBooking = bookingService.createBooking(request);

        // THEN
        assertEquals(now, createdBooking.date());
        assertEquals(request.name(), createdBooking.name());
    }

    @Test
    public void createBookingWhenNoClassAvailable() {
        // GIVEN
        when(studioClassService.getByDate(any())).thenReturn(Optional.empty());
        CreateBookingRequest request = new CreateBookingRequest("test", LocalDate.now());

        // THEN
        assertThrows(NoSuchElementException.class, () -> bookingService.createBooking(request));
    }

    private StudioClass createStudioClass(LocalDate date) {
        StudioClass studioClass = new StudioClass();
        studioClass.setName("test");
        studioClass.setCapacity(10);
        studioClass.setDate(date);

        return studioClassRepository.save(studioClass);
    }
    
}
