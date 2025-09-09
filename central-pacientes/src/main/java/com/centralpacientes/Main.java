package com.centralpacientes;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        PatientService service = new PatientService(new PatientList());
        SwingUtilities.invokeLater(() -> new MainGUI(service).setVisible(true));
    }
}


