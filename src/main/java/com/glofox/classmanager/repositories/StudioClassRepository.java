package com.glofox.classmanager.repositories;

import java.time.LocalDate;
import java.util.Optional;

import com.glofox.classmanager.models.StudioClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudioClassRepository extends JpaRepository<StudioClass, Long> {
    boolean existsByDate(LocalDate date);
    Optional<StudioClass> findByDate(LocalDate date);
}
