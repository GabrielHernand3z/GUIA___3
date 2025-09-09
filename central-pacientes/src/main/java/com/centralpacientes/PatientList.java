package com.centralpacientes;

import java.io.PrintStream;

/**
 * Lista específica de Pacientes basada en lista enlazada simple.
 */
public class PatientList extends SinglyLinkedList<Patient> {

    /** Agrega paciente al final, validando unicidad de ID. */
    public boolean addPatient(Patient p) {
        if (p == null) return false;
        if (findById(p.getId()) != null) {
            return false; // ID duplicado
        }
        addLast(p);
        return true;
    }

    /** Busca paciente por ID. */
    public Patient findById(String id) {
        return findFirst(p -> p.getId().equalsIgnoreCase(id));
    }

    /** Elimina paciente por ID. */
    public boolean deleteById(String id) {
        return removeFirstMatch(p -> p.getId().equalsIgnoreCase(id));
    }

    /** Imprime todos los pacientes. */
    public void printAll(PrintStream out) {
        if (isEmpty()) {
            out.println("No hay pacientes registrados.");
        } else {
            forEach(p -> out.println(p.toString()));
        }
    }
}

