package restaurante.Client;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.List;
import restaurante.Operaciones.MenuDAO;
import restaurante.Operaciones.OrdersDAO;

public class ClientGUI {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 9999;

    public static void main(String[] args) {
        // Ejecutar la GUI en el hilo de eventos
        SwingUtilities.invokeLater(() -> new ClientGUI().createAndShowGUI());
    }

    public void createAndShowGUI() {
        // Crear el frame principal
        JFrame frame = new JFrame("Sistema de Restaurante - Cliente");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Crear panel para los botones y el área de texto
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Crear los botones
        JButton btnViewMenu = new JButton("Ver Menú");
        JButton btnPlaceOrder = new JButton("Realizar Pedido");
        JButton btnSelectTable = new JButton("Seleccionar Mesa");

        // Crear un área de texto para mostrar los resultados
        JTextArea txtArea = new JTextArea(10, 30);
        txtArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtArea);

        // Añadir los componentes al panel
        panel.add(btnViewMenu);
        panel.add(btnPlaceOrder);
        panel.add(btnSelectTable);
        panel.add(scrollPane);

        // Configurar las acciones de los botones
        btnViewMenu.addActionListener(e -> viewMenu(txtArea));
        btnPlaceOrder.addActionListener(e -> placeOrder(txtArea));
        btnSelectTable.addActionListener(e -> selectTable(txtArea));

        // Añadir el panel al frame
        frame.add(panel);
        frame.setVisible(true);
    }

    // Método para ver el menú
    private void viewMenu(JTextArea txtArea) {
        List<String[]> menu = MenuDAO.getAllDishes();
        txtArea.setText("Menú Disponible:\n");
        for (String[] dish : menu) {
            txtArea.append(String.join(", ", dish) + "\n");
        }
    }

    // Método para seleccionar una mesa y mostrar la información de mesa
    private void selectTable(JTextArea txtArea) {
        // Ejemplo de seleccionar mesa (en un caso real, mostrar mesas disponibles)
        txtArea.setText("Seleccione una mesa de la siguiente lista:\n");
        txtArea.append("1. Mesa 1 - Libre\n");
        txtArea.append("2. Mesa 2 - Libre\n");
        txtArea.append("3. Mesa 3 - Ocupada\n");
    }

    // Método para realizar un pedido
    private void placeOrder(JTextArea txtArea) {
        // Este ejemplo es básico. Se puede hacer más dinámico según las interacciones del usuario.
        txtArea.setText("Ingrese el número de la mesa: ");
        // Aquí deberías capturar la mesa seleccionada y el platillo pedido.
        // Ejemplo de agregar un pedido:
        int tableNumber = 1; // Mesa seleccionada
        int menuId = 1; // ID de platillo seleccionado
        int quantity = 2; // Cantidad de platillos
        double totalPrice = 5.99 * quantity; // Precio total del pedido

        OrdersDAO.addOrder(tableNumber, menuId, quantity, totalPrice);
        txtArea.append("Pedido realizado exitosamente.\n");
    }
}
