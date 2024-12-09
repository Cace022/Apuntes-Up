package restaurante.Operaciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import restaurante.Database.Database;

public class OrdersDAO {

    // Agregar una nueva orden
    public static void addOrder(int tableNumber, int menuId, int quantity, double totalPrice) {
        String sql = "INSERT INTO orders(table_number, menu_id, quantity, total_price) VALUES(?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, tableNumber);
            pstmt.setInt(2, menuId);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, totalPrice);
            pstmt.executeUpdate();
            System.out.println("Orden registrada exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al registrar la orden: " + e.getMessage());
        }
    }

    // Obtener todas las órdenes
    public static List<String> getAllOrders() {
        String sql = "SELECT o.id, o.table_number, m.name, o.quantity, o.total_price, o.created_at " +
                     "FROM orders o JOIN menu m ON o.menu_id = m.id ORDER BY o.created_at DESC";
        List<String> orders = new ArrayList<>();
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                orders.add("ID: " + rs.getInt("id") +
                           ", Mesa: " + rs.getInt("table_number") +
                           ", Platillo: " + rs.getString("name") +
                           ", Cantidad: " + rs.getInt("quantity") +
                           ", Precio Total: $" + rs.getDouble("total_price") +
                           ", Fecha: " + rs.getString("created_at"));
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las órdenes: " + e.getMessage());
        }
        return orders;
    }
}
