package com.glofox.classmanager.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "classes")
public class StudioClass {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classes_seq")
    @SequenceGenerator(name = "classes_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Integer capacity;

    @OneToMany(mappedBy = "studioClass")
    private Set<Booking> bookings = new HashSet<>();

    public StudioClass() {
    }

    public StudioClass(Long id, String name, LocalDate date, Integer capacity, Set<Booking> bookings) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.capacity = capacity;
        this.bookings = bookings;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getCapacity() {
        return this.capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Set<Booking> getBookings() {
        return this.bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof StudioClass)) {
            return false;
        }
        StudioClass studioClass = (StudioClass) o;
        return Objects.equals(id, studioClass.id) && Objects.equals(name, studioClass.name) && Objects.equals(date, studioClass.date) && Objects.equals(capacity, studioClass.capacity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date, capacity);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", date='" + getDate() + "'" +
            ", capacity='" + getCapacity() + "'" +
            "}";
    }
    

}
