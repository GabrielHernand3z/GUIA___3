package com.centralpacientes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

/** Ventana principal GUI para la Central de Pacientes. */
public class MainGUI extends JFrame {
    private final PatientService service;
    private final JTextField txtId = new JTextField(10);
    private final JTextField txtName = new JTextField(20);
    private final JTextField txtAge = new JTextField(5);
    private final JTextField txtClinic = new JTextField(15);
    private final PatientTableModel tableModel = new PatientTableModel();
    private final JTable table = new JTable(tableModel);
    private final JScrollPane scroll = new JScrollPane(table); // mostrar/ocultar tabla

    public MainGUI(PatientService service) {
        super("Central de Pacientes — Lista Enlazada Simple");
        this.service = service;
        buildUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);
        hideTable(); // tabla oculta por defecto
    }

    private void buildUI() {
        // Formulario (arriba)
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(4,4,4,4);
        g.gridx=0; g.gridy=0; g.anchor = GridBagConstraints.LINE_END; form.add(new JLabel("ID:"), g);
        g.gridx=1; g.anchor = GridBagConstraints.LINE_START; form.add(txtId, g);

        g.gridx=0; g.gridy=1; g.anchor = GridBagConstraints.LINE_END; form.add(new JLabel("Nombre:"), g);
        g.gridx=1; g.anchor = GridBagConstraints.LINE_START; form.add(txtName, g);

        g.gridx=0; g.gridy=2; g.anchor = GridBagConstraints.LINE_END; form.add(new JLabel("Edad:"), g);
        g.gridx=1; g.anchor = GridBagConstraints.LINE_START; form.add(txtAge, g);

        g.gridx=0; g.gridy=3; g.anchor = GridBagConstraints.LINE_END; form.add(new JLabel("Clínica:"), g);
        g.gridx=1; g.anchor = GridBagConstraints.LINE_START; form.add(txtClinic, g);

        // Botonera
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Registrar");
        JButton btnFind = new JButton("Buscar");
        JButton btnUpdate = new JButton("Modificar");
        JButton btnDelete = new JButton("Eliminar");
        JButton btnList = new JButton("Listar todos");
        JButton btnClear = new JButton("Limpiar");

        btnAdd.addActionListener(this::onAdd);
        btnFind.addActionListener(this::onFind);
        btnUpdate.addActionListener(this::onUpdate);
        btnDelete.addActionListener(this::onDelete);
        btnList.addActionListener(e -> showAll());
        btnClear.addActionListener(e -> clearForm());

        buttons.add(btnAdd);
        buttons.add(btnFind);
        buttons.add(btnUpdate);
        buttons.add(btnDelete);
        buttons.add(btnList);
        buttons.add(btnClear);

        // Tabla (centro): selección llena el formulario
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onTableSelection();
        });

        // Layout principal
        JPanel north = new JPanel(new BorderLayout());
        north.add(form, BorderLayout.CENTER);
        north.add(buttons, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(north, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    /* ===== Acciones ===== */

    private void onAdd(ActionEvent e) {
        try {
            String id = txtId.getText().trim();
            String name = txtName.getText().trim();
            int age = Integer.parseInt(txtAge.getText().trim());
            String clinic = txtClinic.getText().trim();
            boolean ok = service.register(id, name, age, clinic);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Paciente registrado.");
                // Mostrar SOLO el recién agregado
                Patient p = service.find(id);
                showSingle(p);
                // 🔧 Asegura que el layout se refresque la 1ra vez
                this.revalidate();
                this.repaint();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "ID duplicado o datos inválidos.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Edad inválida: debe ser número entero.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onFind(ActionEvent e) {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el ID para buscar.");
            return;
        }
        Patient p = service.find(id);
        if (p == null) {
            JOptionPane.showMessageDialog(this, "No encontrado.");
            hideTable();
        } else {
            // Llenar formulario y mostrar SOLO ese paciente
            txtName.setText(p.getName());
            txtAge.setText(String.valueOf(p.getAge()));
            txtClinic.setText(p.getClinic());
            showSingle(p);
        }
    }

    private void onUpdate(ActionEvent e) {
        String id = txtId.getText().trim();
        if (id.isEmpty()) { JOptionPane.showMessageDialog(this, "Digite el ID a modificar."); return; }

        String name = txtName.getText().trim();
        String ageStr = txtAge.getText().trim();
        String clinic = txtClinic.getText().trim();

        Integer age = null;
        if (!ageStr.isEmpty()) {
            try { age = Integer.parseInt(ageStr); }
            catch (NumberFormatException ex) { JOptionPane.showMessageDialog(this, "Edad inválida."); return; }
        }

        boolean ok = service.update(id, name.isEmpty()? null : name, age, clinic.isEmpty()? null : clinic);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Paciente actualizado.");
            // Mostrar SOLO el actualizado
            Patient p = service.find(id);
            showSingle(p);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar (verifique ID).", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void onDelete(ActionEvent e) {
        String id = txtId.getText().trim();
        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite el ID a eliminar.");
            return;
        }
        int ans = JOptionPane.showConfirmDialog(this,
                "¿Eliminar paciente " + id + "?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (ans == JOptionPane.YES_OPTION) {
            boolean ok = service.delete(id);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Paciente eliminado.");
                // Si la tabla tiene varios, volvemos a cargar TODOS sin el eliminado.
                // Si sólo mostraba uno (el eliminado), limpiamos y ocultamos.
                if (tableModel.hasRows() && tableModel.getRowCount() > 1) {
                    tableModel.setDataFrom(service.getAll());
                    showTable();
                } else {
                    tableModel.clear();
                    hideTable();
                }
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "No encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /* ===== Helpers de presentación ===== */

    /** Muestra TODA la lista y hace visible la tabla. */
    private void showAll() {
        tableModel.setDataFrom(service.getAll());
        showTable();
    }

    /** Muestra un único paciente y hace visible la tabla (si p es null, oculta). */
    private void showSingle(Patient p) {
        if (p == null) {
            tableModel.clear();
            hideTable();
            return;
        }
        tableModel.setSingle(p);
        showTable();
    }

    /** Lógica para mostrar la tabla. */
    private void showTable() {
        scroll.setVisible(true);
        // 🔧 Revalidar todo el contenedor para asegurar primer pintado
        scroll.revalidate();
        this.revalidate();
        this.repaint();
    }

    /** Lógica para ocultar la tabla (cuando no hay nada que mostrar). */
    private void hideTable() {
        scroll.setVisible(false);
        scroll.revalidate();
        this.revalidate();
        this.repaint();
    }

    /** Cuando seleccionas una fila, llenamos el formulario. */
    private void onTableSelection() {
        int row = table.getSelectedRow();
        Patient p = tableModel.getAt(row);
        if (p != null) {
            txtId.setText(p.getId());
            txtName.setText(p.getName());
            txtAge.setText(String.valueOf(p.getAge()));
            txtClinic.setText(p.getClinic());
        }
    }

    private void clearForm() {
        txtId.setText("");
        txtName.setText("");
        txtAge.setText("");
        txtClinic.setText("");
        table.clearSelection();
    }
}
