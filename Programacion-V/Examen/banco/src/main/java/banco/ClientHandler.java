package banco;

import com.google.gson.*;
import java.io.*;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private JsonUsuarioManager jsonUsuarioManager;
    private List<Usuario> usuarios;

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        this.jsonUsuarioManager = new JsonUsuarioManager();
    }

    @Override
    public void run() {
        try (
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            usuarios = jsonUsuarioManager.leerUsuarios();
    
            // Verificar si se cargaron usuarios
            if (usuarios == null || usuarios.isEmpty()) {
                System.out.println("Error: Lista de usuarios vacía o no cargada.");
                out.println("Error interno del servidor. Intente más tarde.");
                return;
            } else {
                System.out.println("Usuarios cargados en el servidor: " + usuarios);
            }
    
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Mensaje recibido del cliente: " + message);
    
                if (message.startsWith("LOGIN")) {
                    // Usar una expresión regular para dividir el mensaje
                    String regex = "LOGIN\\s+\"([\\p{L}\\p{M}\\s]+)\"\\s+(\\S+)";
                    java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
                    java.util.regex.Matcher matcher = pattern.matcher(message);
    
                    if (matcher.matches()) {
                        String nombre = matcher.group(1); // Captura el nombre del usuario
                        String password = matcher.group(2); // Captura la contraseña
                        System.out.println("Intento de autenticación - Nombre: " + nombre + ", Password: " + password);
    
                        Usuario usuario = autenticarUsuario(nombre, password);
    
                        if (usuario != null) {
                            out.println("Autenticación exitosa.");
                            System.out.println("Usuario autenticado: " + usuario);
                        } else {
                            out.println("Usuario o contraseña incorrectos.");
                            System.out.println("Fallo de autenticación para: " + nombre);
                        }
                    } else {
                        System.out.println("El mensaje no coincide con el formato esperado.");
                        out.println("Error: Formato inválido.");
                    }
                } else if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Cliente desconectado.");
                    break;
                } else {
                    System.out.println("Comando no reconocido: " + message);
                    out.println("Error: Comando no reconocido.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error en la comunicación con el cliente.");
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

    // Método para autenticar a un usuario
    private Usuario autenticarUsuario(String nombre, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre) && usuario.getPassword().equals(password)) {
                return usuario;  // Devolver el usuario si la autenticación es correcta
            }
        }
        return null;  // Si no se encuentra el usuario o la contraseña no es correcta
    }
}
