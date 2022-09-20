package com.glofox.classmanager.models;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "bookings")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookings_seq")
    @SequenceGenerator(name = "bookings_seq", allocationSize = 1)
    private Long id;
    
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false, updatable = false)
    private StudioClass studioClass;

    public Booking() {
    }

    public Booking(Long id, String name, StudioClass studioClass) {
        this.id = id;
        this.name = name;
        this.studioClass = studioClass;
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

    public StudioClass getStudioClass() {
        return this.studioClass;
    }

    public void setStudioClass(StudioClass studioClass) {
        this.studioClass = studioClass;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Booking)) {
            return false;
        }
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(name, booking.name) && Objects.equals(studioClass, booking.studioClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, studioClass);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", studioClass='" + getStudioClass() + "'" +
            "}";
    }

}
