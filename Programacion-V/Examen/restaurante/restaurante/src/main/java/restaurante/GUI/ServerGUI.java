package restaurante.GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import restaurante.Operaciones.MenuDAO;
import restaurante.Operaciones.OrdersDAO;

public class ServerGUI {
    private JFrame frame;
    private JTextArea ordersArea;

    public ServerGUI() {
        frame = new JFrame("Servidor - Restaurante");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Panel para órdenes
        ordersArea = new JTextArea();
        ordersArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(ordersArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Botones
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refrescar Órdenes");
        JButton menuButton = new JButton("Editar Menú");
        buttonPanel.add(refreshButton);
        buttonPanel.add(menuButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Acciones
        refreshButton.addActionListener(e -> loadOrders());
        menuButton.addActionListener(e -> new MenuEditorGUI());

        frame.setVisible(true);
    }

    private void loadOrders() {
        List<String> orders = OrdersDAO.getAllOrders();
        ordersArea.setText("");
        orders.forEach(order -> ordersArea.append(order + "\n"));
    }

    public static void main(String[] args) {
        new ServerGUI();
    }
}
