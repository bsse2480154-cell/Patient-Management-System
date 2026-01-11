import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class PatientManagementGUI {
    private PatientOperations manager;
    private JFrame frame;
    private JTable table;
    private DefaultTableModel model;

    public PatientManagementGUI(PatientOperations manager) {
        this.manager = manager;
        initialize();
        refreshTable();
    }

    private void initialize() {
        // Frame setup
        frame = new JFrame("Patient Management System");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(245, 245, 245));

        // Header panel
        JPanel header = new JPanel();
        Color oliveGreen = new Color(85, 107, 47);
        header.setBackground(oliveGreen);
        JLabel title = new JLabel("Patient Management System", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title);
        header.setBorder(new EmptyBorder(20, 0, 20, 0));
        frame.add(header, BorderLayout.NORTH);

        // Table setup
        model = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Status"}, 0);
        table = new JTable(model);
        table.setEnabled(false);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(oliveGreen);
        table.getTableHeader().setForeground(Color.WHITE);

        // Center all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(scrollPane, BorderLayout.CENTER);

        // Buttons panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 6, 15, 10));
        panel.setBorder(new EmptyBorder(10, 10, 20, 10));
        panel.setBackground(new Color(245, 245, 245));

        JButton addButton = createButton("Add Patient", oliveGreen);
        JButton displayButton = createButton("Display Patients", oliveGreen);
        JButton sortButton = createButton("Sort by ID", oliveGreen);
        JButton searchButton = createButton("Search by ID", oliveGreen);
        JButton updateButton = createButton("Update Status", oliveGreen);
        JButton deleteButton = createButton("Delete Patient", oliveGreen);

        panel.add(addButton);
        panel.add(displayButton);
        panel.add(sortButton);
        panel.add(searchButton);
        panel.add(updateButton);
        panel.add(deleteButton);

        frame.add(panel, BorderLayout.SOUTH);

        // Button actions
        addButton.addActionListener(e -> addPatient());
        displayButton.addActionListener(e -> refreshTable());
        sortButton.addActionListener(e -> {
            manager.sortPatients();
            JOptionPane.showMessageDialog(frame, "Patients sorted by ID.");
            refreshTable();
        });
        searchButton.addActionListener(e -> searchPatient());
        updateButton.addActionListener(e -> updateStatus());
        deleteButton.addActionListener(e -> deletePatient());

        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);
    }

    private JButton createButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return button;
    }

    private void refreshTable() {
        model.setRowCount(0); // Clear table
        for (int i = 0; i < manager.getCount(); i++) {
            Patient p = manager.getPatient(i);
            model.addRow(new Object[]{p.getId(), p.getName(), p.getAge(), p.getStatus()});
        }
    }

    private void addPatient() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID:"));
            String name = JOptionPane.showInputDialog("Enter Name:");
            int age = Integer.parseInt(JOptionPane.showInputDialog("Enter Age:"));
            manager.addPatient(new Patient(id, name, age));
            JOptionPane.showMessageDialog(frame, "Patient added successfully.");
            refreshTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void searchPatient() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID to search:"));
            int index = manager.searchPatient(id, 0, manager.getCount() - 1);
            if (index != -1) {
                Patient p = manager.getPatient(index);
                JOptionPane.showMessageDialog(frame, "Found: " + p.getName() + " | Status: " + p.getStatus());
            } else {
                JOptionPane.showMessageDialog(frame, "Patient not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void updateStatus() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID to update status:"));
            String status = JOptionPane.showInputDialog("Enter new status (Admitted/Recovered):");
            if (manager.updateStatus(id, status)) {
                JOptionPane.showMessageDialog(frame, "Status updated successfully.");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Patient not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    private void deletePatient() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Enter ID to delete:"));
            if (manager.deletePatient(id)) {
                JOptionPane.showMessageDialog(frame, "Patient deleted successfully.");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(frame, "Patient not found.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input!");
        }
    }

    public static void main(String[] args) {
        String filePath = System.getProperty("user.dir") + java.io.File.separator + "patients.txt";
        PatientOperations manager = new PatientOperations(filePath);
        new PatientManagementGUI(manager);
    }
}
