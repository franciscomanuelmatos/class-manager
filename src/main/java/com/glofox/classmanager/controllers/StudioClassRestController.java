package com.glofox.classmanager.controllers;

import java.util.List;

import com.glofox.classmanager.dtos.CreateStudioClassRequest;
import com.glofox.classmanager.dtos.StudioClassDTO;
import com.glofox.classmanager.services.StudioClassService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/classes")
public class StudioClassRestController {
    private final StudioClassService studioClassService;

    public StudioClassRestController(StudioClassService studioClassService) {
        this.studioClassService = studioClassService;
    }

    @PostMapping
    public List<StudioClassDTO> createClasses(@RequestBody @Valid CreateStudioClassRequest classesToCreateDTO) {
        return studioClassService.createStudioClasses(classesToCreateDTO);
    }
}
