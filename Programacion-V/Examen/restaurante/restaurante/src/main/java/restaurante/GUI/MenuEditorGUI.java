package restaurante.GUI;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import restaurante.Operaciones.MenuDAO;
import restaurante.Operaciones.OrdersDAO;
import restaurante.Database.MenuTableModel;

public class MenuEditorGUI {
    private JFrame frame;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField imageField;
    private JTable menuTable;
    private MenuTableModel tableModel;

    public MenuEditorGUI() {
        frame = new JFrame("Editor de Menú");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel superior: formulario para agregar/editar platillos
        JPanel formPanel = new JPanel(new GridLayout(4, 2));
        formPanel.add(new JLabel("Nombre:"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Precio:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        formPanel.add(new JLabel("Ruta Imagen:"));
        imageField = new JTextField();
        formPanel.add(imageField);

        JButton addButton = new JButton("Agregar");
        JButton updateButton = new JButton("Actualizar");
        formPanel.add(addButton);
        formPanel.add(updateButton);

        frame.add(formPanel, BorderLayout.NORTH);

        // Tabla para mostrar el menú
        tableModel = new MenuTableModel();
        menuTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(menuTable);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Panel inferior: botón para eliminar platillos
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Eliminar");
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Acciones de los botones
        addButton.addActionListener(e -> addDish());
        updateButton.addActionListener(e -> updateDish());
        deleteButton.addActionListener(e -> deleteDish());

        // Cargar los datos iniciales
        loadMenu();

        frame.setVisible(true);
    }

    private void loadMenu() {
        List<String[]> menu = MenuDAO.getAllDishes(); 
        tableModel.setMenuData(menu);
    }

    private void addDish() {
        String name = nameField.getText();
        String priceText = priceField.getText();
        String image = imageField.getText();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nombre y precio son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            MenuDAO.addDish(name, price, image);
            loadMenu();
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDish() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Selecciona un platillo para actualizar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String name = nameField.getText();
        String priceText = priceField.getText();
        String image = imageField.getText();

        if (name.isEmpty() || priceText.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nombre y precio son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int id = tableModel.getDishId(selectedRow);
            MenuDAO.updateDish(id, name, price, image);
            loadMenu();
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "El precio debe ser un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteDish() {
        int selectedRow = menuTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(frame, "Selecciona un platillo para eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int id = tableModel.getDishId(selectedRow);
        MenuDAO.deleteDish(id);
        loadMenu();
    }

    private void clearFields() {
        nameField.setText("");
        priceField.setText("");
        imageField.setText("");
    }
}
