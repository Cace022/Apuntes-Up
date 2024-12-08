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
            // Crear flujos de entrada y salida para la comunicación
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Cargar los usuarios desde el archivo JSON
            usuarios = jsonUsuarioManager.leerUsuarios();

            String message;
            while ((message = in.readLine()) != null) {
                // Procesar el mensaje recibido
                System.out.println("Mensaje recibido: " + message);

                // Si el mensaje es una solicitud de login
                if (message.startsWith("LOGIN")) {
                    String[] parts = message.split(" ");
                    String nombre = parts[1];
                    String password = parts[2];

                    Usuario usuario = autenticarUsuario(nombre, password);
                    if (usuario != null) {
                        out.println("Autenticación exitosa.");
                        System.out.println("Usuario autenticado: " + usuario.getNombre());

                        // Aquí puedes implementar las operaciones adicionales (transferencia, consulta de saldo, etc.)
                    } else {
                        out.println("Usuario o contraseña incorrectos.");
                    }
                }

                // Aquí se pueden manejar otras operaciones como "TRANSFERIR", "CONSULTAR_SALDO", etc.
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Cliente desconectado.");
                    break;
                }
            }
        } catch (IOException e) {
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
