package com.centralpacientes;

/**
 * Capa de servicio con reglas simples de negocio.
 */
public class PatientService {
    private final PatientList repository;

    public PatientService(PatientList repository) {
        this.repository = repository;
    }

    /** Registra un nuevo paciente si el ID es único. */
    public boolean register(String id, String name, int age, String clinic) {
        Patient p = new Patient(id, name, age, clinic);
        return repository.addPatient(p);
    }

    /** Modifica datos (excepto ID). Retorna true si el paciente existe. */
    public boolean update(String id, String newName, Integer newAge, String newClinic) {
        Patient p = repository.findById(id);
        if (p == null) return false;
        if (newName != null && !newName.isBlank()) p.setName(newName.trim());
        if (newAge != null && newAge >= 0) p.setAge(newAge);
        if (newClinic != null && !newClinic.isBlank()) p.setClinic(newClinic.trim());
        return true;
    }

    public Patient find(String id) {
        return repository.findById(id);
    }

    public boolean delete(String id) {
        return repository.deleteById(id);
    }

    public PatientList getAll() {
        return repository;
    }
}

