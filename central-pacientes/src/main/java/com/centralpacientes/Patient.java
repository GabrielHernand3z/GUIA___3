package com.centralpacientes;

import java.util.Objects;

/**
 * Entidad de dominio: Paciente.
 * Cada paciente tiene un ID único, nombre, edad y clínica.
 */
public class Patient {
    private final String id;   // Identificador único
    private String name;
    private int age;
    private String clinic;

    public Patient(String id, String name, int age, String clinic) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("El ID no puede ser vacío");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("El nombre no puede ser vacío");
        if (age < 0) throw new IllegalArgumentException("La edad no puede ser negativa");
        if (clinic == null || clinic.isBlank()) throw new IllegalArgumentException("La clínica no puede ser vacía");
        this.id = id.trim();
        this.name = name.trim();
        this.age = age;
        this.clinic = clinic.trim();
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getClinic() { return clinic; }
    public void setClinic(String clinic) { this.clinic = clinic; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id.equals(patient.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("Paciente{id='%s', nombre='%s', edad=%d, clinica='%s'}", id, name, age, clinic);
    }
}

