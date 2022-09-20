package com.glofox.classmanager.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.glofox.classmanager.dtos.CreateStudioClassRequest;
import com.glofox.classmanager.dtos.StudioClassDTO;
import com.glofox.classmanager.mappers.StudioClassMapper;
import com.glofox.classmanager.models.StudioClass;
import com.glofox.classmanager.repositories.StudioClassRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StudioClassService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudioClassService.class);

    private final StudioClassRepository studioClassRepository;
    private final StudioClassMapper studioClassMapper;


    public StudioClassService(StudioClassRepository studioClassRepository, StudioClassMapper studioClassMapper) {
        this.studioClassRepository = studioClassRepository;
        this.studioClassMapper = studioClassMapper;
    }

    public List<StudioClassDTO> createStudioClasses(CreateStudioClassRequest classesToCreateDTO) {
        LOGGER.info("CreateStudioClassRequest: {}", classesToCreateDTO);

        if (classesToCreateDTO.endDate().isBefore(classesToCreateDTO.startDate())) {
            throw new IllegalArgumentException("endDate cannot be before startDate");
        }

        List<StudioClass> classesToCreate = new ArrayList<>();

        for (LocalDate current = classesToCreateDTO.startDate(); current.isBefore(classesToCreateDTO.endDate()) || current.isEqual(classesToCreateDTO.endDate()); current = current.plusDays(1)) {
            if (dateHasNoClassScheduled(current)) {
                StudioClass classToCreate = new StudioClass();
                classToCreate.setName(classesToCreateDTO.name());
                classToCreate.setCapacity(classesToCreateDTO.capacity());
                classToCreate.setDate(current);
                classesToCreate.add(classToCreate);
            }
        }

        studioClassRepository.saveAll(classesToCreate);
        LOGGER.info("Created {} classes", classesToCreate.size());

        return classesToCreate.stream()
                            .map(studioClass -> studioClassMapper.modelToDTO(studioClass))
                            .collect(Collectors.toList());
    }

    public Optional<StudioClass> getByDate(LocalDate date) {
        return studioClassRepository.findByDate(date);
    }

    private boolean dateHasNoClassScheduled(LocalDate date) {
        return !studioClassRepository.existsByDate(date);
    }

}
