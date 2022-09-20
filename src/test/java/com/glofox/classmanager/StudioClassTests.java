package com.glofox.classmanager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import com.glofox.classmanager.dtos.CreateStudioClassRequest;
import com.glofox.classmanager.dtos.StudioClassDTO;
import com.glofox.classmanager.models.StudioClass;
import com.glofox.classmanager.repositories.StudioClassRepository;
import com.glofox.classmanager.services.StudioClassService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class StudioClassTests {
    @Autowired
    private StudioClassService studioClassService;

    @MockBean
    private StudioClassRepository studioClassRepository;

    @Test
    public void createStudioClassesWhenDateAvailable() {
        // GIVEN
        when(studioClassRepository.existsByDate(any())).thenReturn(false);
        when(studioClassRepository.saveAll(anyIterable())).thenReturn(Collections.emptyList());
        CreateStudioClassRequest request = new CreateStudioClassRequest("test", LocalDate.now().minusDays(2), LocalDate.now(), 10);

        // WHEN
        List<StudioClassDTO> classesCreated = studioClassService.createStudioClasses(request);

        // THEN
        assertEquals(3, classesCreated.size());
        verify(studioClassRepository, times(1)).saveAll(List.of(
            new StudioClass(null, "test", LocalDate.now().minusDays(2), 10, Collections.emptySet()),
            new StudioClass(null, "test", LocalDate.now().minusDays(1), 10, Collections.emptySet()),
            new StudioClass(null, "test", LocalDate.now(), 10, Collections.emptySet()) 
        ));
    }

    @Test
    public void createStudioClassesCreatesClassWhenStartAndEndDateAreEqual() {
        // GIVEN
        when(studioClassRepository.existsByDate(any())).thenReturn(false);
        when(studioClassRepository.saveAll(anyIterable())).thenReturn(Collections.emptyList());
        CreateStudioClassRequest request = new CreateStudioClassRequest("test", LocalDate.now(), LocalDate.now(), 10);

        // WHEN
        List<StudioClassDTO> classesCreated = studioClassService.createStudioClasses(request);

        // THEN
        assertEquals(1, classesCreated.size());
        StudioClassDTO createdClass = classesCreated.get(0);
        assertEquals(request.name(), createdClass.name());
        assertEquals(request.capacity(), createdClass.capacity());
        assertEquals(request.startDate(), createdClass.date());

        verify(studioClassRepository, times(1)).saveAll(List.of(new StudioClass(null, "test", LocalDate.now(), 10, Collections.emptySet())));
    }

    @Test
    public void createStudioClassesWhenNoDataAvailable() {
        // GIVEN
        when(studioClassRepository.existsByDate(any())).thenReturn(true);
        when(studioClassRepository.saveAll(anyIterable())).thenReturn(Collections.emptyList());
        CreateStudioClassRequest request = new CreateStudioClassRequest("test", LocalDate.now().minusDays(2), LocalDate.now(), 10);

        // WHEN
        List<StudioClassDTO> classesCreated = studioClassService.createStudioClasses(request);

        // THEN
        assertEquals(0, classesCreated.size());
        verify(studioClassRepository, times(1)).saveAll(Collections.emptyList());
    }

    @Test
    public void createStudioClassesStartDateMustBeBeforeEndDate() {
        // GIVEN
        CreateStudioClassRequest request = new CreateStudioClassRequest("test", LocalDate.now().plusDays(1), LocalDate.now(), 1);

        // THEN
        assertThrows(IllegalArgumentException.class, () -> studioClassService.createStudioClasses(request));
    }
    
}
