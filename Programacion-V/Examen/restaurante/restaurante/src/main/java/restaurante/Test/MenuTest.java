package restaurante.Test;

import java.util.List;
import restaurante.Operaciones.MenuDAO;

public class MenuTest {
    public static void main(String[] args) {
        // Agregar platillos iniciales
        System.out.println("Agregando platillos...");
        MenuDAO.addDish("Hamburguesa", 5.99, null); // Usando imagen nula
        MenuDAO.addDish("Pizza", 8.99, null); // Usando imagen nula

        // Listar platillos
        System.out.println("\nPlatillos disponibles después de agregar...");
        List<String[]> dishes = MenuDAO.getAllDishes(); // Usamos List<String[]>
        for (String[] dish : dishes) {
            System.out.println("ID: " + dish[0] + ", Nombre: " + dish[1] + ", Precio: $" + dish[2]);
        }

        // Prueba de inserción con datos repetidos (debería manejarse correctamente)
        System.out.println("\nPrueba de inserción con datos repetidos...");
        MenuDAO.addDish("Hamburguesa", 5.99, null); // Platillo repetido
        dishes = MenuDAO.getAllDishes();
        for (String[] dish : dishes) {
            System.out.println("ID: " + dish[0] + ", Nombre: " + dish[1] + ", Precio: $" + dish[2]);
        }

        // Intentar actualizar un platillo con ID no existente (debe mostrar un mensaje de error o no hacer cambios)
        System.out.println("\nIntentando actualizar platillo con ID no existente...");
        MenuDAO.updateDish(999, "Hamburguesa Especial", 6.99, null); // ID no existente

        // Intentar eliminar un platillo con ID no existente (debe mostrar un mensaje de error o no hacer cambios)
        System.out.println("\nIntentando eliminar platillo con ID no existente...");
        MenuDAO.deleteDish(999); // ID no existente

        // Eliminar un platillo existente (ID 1 por ejemplo)
        System.out.println("\nEliminando platillo con ID 1...");
        MenuDAO.deleteDish(1);

        // Verificar cambios después de eliminación
        System.out.println("\nPlatillos después de la eliminación de ID 1:");
        dishes = MenuDAO.getAllDishes();
        for (String[] dish : dishes) {
            System.out.println("ID: " + dish[0] + ", Nombre: " + dish[1] + ", Precio: $" + dish[2]);
        }

        // Prueba de inserción con valores extremos
        System.out.println("\nPrueba de inserción con valores extremos...");
        MenuDAO.addDish("Platillo Carísimo", 1000000.00, null); // Precio extremadamente alto
        MenuDAO.addDish("Platillo Gratis", 0.00, null); // Precio cero
        MenuDAO.addDish("Platillo con Nombre Muy Largo", 10.99, null); // Nombre muy largo

        // Verificar la inserción de platillos con valores extremos
        dishes = MenuDAO.getAllDishes();
        for (String[] dish : dishes) {
            System.out.println("ID: " + dish[0] + ", Nombre: " + dish[1] + ", Precio: $" + dish[2]);
        }

        // Prueba de actualización de platillo con un valor vacío para la imagen (debe permitir una imagen vacía)
        System.out.println("\nPrueba de actualización con imagen vacía...");
        MenuDAO.updateDish(2, "Pizza Especial", 9.99, ""); // Imagen vacía (string vacío)
        
        // Verificar los cambios después de la actualización
        System.out.println("\nPlatillos después de la actualización con imagen vacía:");
        dishes = MenuDAO.getAllDishes();
        for (String[] dish : dishes) {
            System.out.println("ID: " + dish[0] + ", Nombre: " + dish[1] + ", Precio: $" + dish[2]);
        }

        // Verificar que no haya platillos con imágenes nulas o vacías que no deberían mostrarse
        System.out.println("\nVerificando platillos con imágenes nulas o vacías...");
        dishes = MenuDAO.getAllDishes();
        for (String[] dish : dishes) {
            if (dish[3] == null || dish[3].isEmpty()) {
                System.out.println("Platillo sin imagen: " + dish[1]);
            }
        }

        // Prueba de visualización de todos los platillos disponibles después de todas las operaciones
        System.out.println("\nPlatillos disponibles después de todas las operaciones:");
        dishes = MenuDAO.getAllDishes();
        for (String[] dish : dishes) {
            System.out.println("ID: " + dish[0] + ", Nombre: " + dish[1] + ", Precio: $" + dish[2]);
        }
    }
}
