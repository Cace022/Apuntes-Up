package restaurante;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import restaurante.Operaciones.MenuDAO;
import restaurante.Operaciones.OrdersDAO;

class ClientHandler implements Runnable {
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
            InputStream input = socket.getInputStream();
            OutputStream output = socket.getOutputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            PrintWriter writer = new PrintWriter(output, true)
        ) {
            writer.println("¡Bienvenido al sistema del restaurante!");

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println("Mensaje recibido: " + message);

                if (message.startsWith("ORDER")) {
                    processOrder(message, writer);
                } else if (message.equals("MENU")) {
                    sendMenu(writer);
                } else if (message.equals("EXIT")) {
                    writer.println("Adiós.");
                    break;
                } else {
                    writer.println("Comando no reconocido.");
                }
            }
        } catch (IOException e) {
            System.out.println("Error al manejar el cliente: " + e.getMessage());
        }
    }

    private void sendMenu(PrintWriter writer) {
        writer.println("MENÚ:");
        // Cambiar para obtener el menú como una lista de platillos con formato
        MenuDAO.getAllDishes().forEach(dish -> writer.println("ID: " + dish[0] + " - Nombre: " + dish[1] + " - Precio: $" + dish[2]));
    }

    private void processOrder(String message, PrintWriter writer) {
        String[] parts = message.split(";");
        if (parts.length >= 4) {
            try {
                int tableNumber = Integer.parseInt(parts[1].split("=")[1]);
                int menuId = Integer.parseInt(parts[2].split("=")[1]);
                int quantity = Integer.parseInt(parts[3].split("=")[1]);

                // Obtén el precio del platillo
                String[] dish = MenuDAO.getAllDishes().stream()
                                        .filter(d -> d[0].equals(String.valueOf(menuId)))
                                        .findFirst()
                                        .orElse(null);

                if (dish != null) {
                    double price = Double.parseDouble(dish[2]); // Asumiendo que el precio está en la posición 2
                    double totalPrice = price * quantity;

                    // Registra la orden
                    OrdersDAO.addOrder(tableNumber, menuId, quantity, totalPrice);
                    writer.println("Orden registrada: Mesa " + tableNumber + ", Platillo ID " + menuId + ", Total $" + totalPrice);
                } else {
                    writer.println("Error: Platillo no encontrado.");
                }
            } catch (Exception e) {
                writer.println("Error al procesar la orden: " + e.getMessage());
            }
        } else {
            writer.println("Formato de orden inválido.");
        }
    }
}
