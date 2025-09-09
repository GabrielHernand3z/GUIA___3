package com.centralpacientes;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

/** TableModel para mostrar pacientes en una JTable. */
public class PatientTableModel extends AbstractTableModel {
    private final String[] cols = {"ID", "Nombre", "Edad", "Clínica"};
    private final List<Patient> data = new ArrayList<>();

    /** Muestra toda la lista de pacientes. */
    public void setDataFrom(PatientList list) {
        data.clear();
        if (list != null) {
            list.forEach(data::add);
        }
        fireTableDataChanged();
    }

    /** Muestra un único paciente (o nada si es null). */
    public void setSingle(Patient p) {
        data.clear();
        if (p != null) data.add(p);
        fireTableDataChanged();
    }

    /** Limpia todo. */
    public void clear() {
        data.clear();
        fireTableDataChanged();
    }

    public Patient getAt(int row) {
        if (row < 0 || row >= data.size()) return null;
        return data.get(row);
    }

    @Override public int getRowCount() { return data.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Patient p = data.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getId();
            case 1: return p.getName();
            case 2: return p.getAge();
            case 3: return p.getClinic();
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int r, int c) { return false; }

    /** ¿Tenemos algo para mostrar? */
    public boolean hasRows() {
        return !data.isEmpty();
    }
}

