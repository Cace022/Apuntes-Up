package banco;

import com.google.gson.*;
import java.io.*;
import java.nio.file.*;
import java.util.List;

public class JsonUsuarioManager {

    private static final String FILE_PATH = "C:/Users/corre/Documents/Apuntes-Up/Programacion-V/Examen/banco/src/main/resources/users.json";

    private Gson gson;

    public JsonUsuarioManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    // Clase contenedora para reflejar la estructura del JSON
    private static class UsuarioContainer {
        List<Usuario> usuarios;
    }

    // Leer los usuarios del archivo JSON
    public List<Usuario> leerUsuarios() throws IOException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Archivo JSON no encontrado en la ruta: " + FILE_PATH);
            return List.of();  // Si el archivo no existe, devolvemos una lista vacía
        }

        // Leer el contenido del archivo y convertirlo a un objeto UsuarioContainer
        String jsonContent = new String(Files.readAllBytes(file.toPath()));
        UsuarioContainer container = gson.fromJson(jsonContent, UsuarioContainer.class);
        System.out.println("Usuarios leídos del archivo JSON: " + container.usuarios);
        return container.usuarios;  // Devolver la lista de usuarios
    }

    // Guardar los usuarios al archivo JSON
    public void guardarUsuarios(List<Usuario> usuarios) throws IOException {
        UsuarioContainer container = new UsuarioContainer();
        container.usuarios = usuarios;  // Asignar la lista de usuarios al contenedor

        // Convertir el objeto a formato JSON y escribirlo en el archivo
        String jsonContent = gson.toJson(container);
        Files.write(Paths.get(FILE_PATH), jsonContent.getBytes());  // Escribir el JSON en el archivo
    }

    // Agregar un nuevo usuario al archivo JSON
    public void agregarUsuario(Usuario usuario) throws IOException {
        List<Usuario> usuarios = leerUsuarios();
        usuarios.add(usuario);  // Agregar el nuevo usuario
        guardarUsuarios(usuarios);  // Guardar la lista actualizada de usuarios
    }
}