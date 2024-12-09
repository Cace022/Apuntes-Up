package restaurante.Operaciones;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import restaurante.Database.Database;

public class MenuDAO {

    private static final String DB_URL = "jdbc:sqlite:restaurant.db";

    // Obtener todos los platillos como una lista de arreglos String[] para la tabla
    public static List<String[]> getAllDishes() {
        List<String[]> menuData = new ArrayList<>();
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {
            String sql = "SELECT * FROM menu";  // Cambié 'platillos' por 'menu'
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                // Crear un arreglo con los valores de las columnas (ID, Nombre, Precio, Imagen)
                String[] dish = new String[4]; // ID, Nombre, Precio, Imagen
                dish[0] = String.valueOf(rs.getInt("id"));
                dish[1] = rs.getString("name");  // Cambié 'nombre' por 'name' para coincidir con la base de datos
                dish[2] = String.valueOf(rs.getDouble("price"));  // Cambié 'precio' por 'price'
                dish[3] = rs.getString("image");  // Cambié 'imagen' por 'image'
                
                // Agregar el arreglo a la lista
                menuData.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return menuData;
    }

    // Método para agregar un nuevo platillo
    public static void addDish(String name, double price, String imagePath) {
        String sql = "INSERT INTO menu (name, price, image) VALUES (?, ?, ?)";  // Cambié 'platillos' por 'menu'
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setString(3, imagePath); // Asumimos que la imagen es un string con la ruta o URL de la imagen
            
            pstmt.executeUpdate();
            System.out.println("Platillo agregado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al agregar platillo: " + e.getMessage());
        }
    }

    // Actualizar un platillo (requiere convertir la imagen a byte[])
    public static void updateDish(int id, String name, double price, String imagePath) {
        String sql = "UPDATE menu SET name = ?, price = ?, image = ? WHERE id = ?";  // Cambié 'platillos' por 'menu'
        
        // Convertir la imagen a byte[] si es necesario
        byte[] imageBytes = null;
        try {
            if (imagePath != null && !imagePath.isEmpty()) {
                imageBytes = imagePath.getBytes();  // Aquí asumimos que la imagen es una ruta de archivo. Si necesitas una imagen real, puedes leer el archivo y convertirlo a byte[]
            }
        } catch (Exception e) {
            System.out.println("Error al convertir la imagen a bytes: " + e.getMessage());
        }
        
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setDouble(2, price);
            pstmt.setBytes(3, imageBytes); // Establecer la imagen como un arreglo de bytes
            pstmt.setInt(4, id);
            
            pstmt.executeUpdate();
            System.out.println("Platillo actualizado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar platillo: " + e.getMessage());
        }
    }

    // Eliminar un platillo
    public static void deleteDish(int id) {
        String sql = "DELETE FROM menu WHERE id = ?";  // Cambié 'platillos' por 'menu'
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            System.out.println("Platillo eliminado exitosamente.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar platillo: " + e.getMessage());
        }
    }
}
