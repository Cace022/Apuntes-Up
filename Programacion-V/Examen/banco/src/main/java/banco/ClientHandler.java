package banco;

import banco.operaciones.*;
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
                    handleLogin(message, out);
                } else if (message.startsWith("CONSULTA")) {
                    handleConsulta(message, out);
                } else if (message.startsWith("RETIRO")) {
                    handleRetiro(message, out);
                } else if (message.startsWith("TRANSFERENCIA")) {
                    handleTransferencia(message, out);
                } else if (message.startsWith("DEPOSITO")) {
                    handleDeposito(message, out);
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

    private void handleLogin(String message, PrintWriter out) {
        String regex = "LOGIN\\s+\"([\\p{L}\\p{M}\\s]+)\"\\s+(\\S+)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);

        if (matcher.matches()) {
            String nombre = matcher.group(1);
            String password = matcher.group(2);
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
    }

    private void handleConsulta(String message, PrintWriter out) {
        String regex = "CONSULTA\\s+\"([\\p{L}\\p{M}\\s]+)\"";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);

        if (matcher.matches()) {
            String nombre = matcher.group(1);
            Usuario usuario = buscarUsuario(nombre);

            if (usuario != null) {
                OperacionConsulta operacionConsulta = new OperacionConsulta();
                operacionConsulta.consultarSaldo(usuario);
                out.println("Consulta realizada. Saldo: $" + usuario.getSaldo());
            } else {
                out.println("Usuario no encontrado.");
            }
        } else {
            out.println("Error: Formato inválido.");
        }
    }

    private void handleRetiro(String message, PrintWriter out) {
        String regex = "RETIRO\\s+\"([\\p{L}\\p{M}\\s]+)\"\\s+(\\d+(\\.\\d{1,2})?)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);

        if (matcher.matches()) {
            String nombre = matcher.group(1);
            double monto = Double.parseDouble(matcher.group(2));
            Usuario usuario = buscarUsuario(nombre);

            if (usuario != null) {
                OperacionRetiro operacionRetiro = new OperacionRetiro();
                operacionRetiro.retirar(usuario, monto);
                try {
                    jsonUsuarioManager.guardarUsuarios(usuarios); // Actualizar JSON
                } catch (IOException e) {
                    System.err.println("Error al guardar usuarios en el archivo JSON.");
                    e.printStackTrace();
                }
                out.println("Retiro realizado. Nuevo saldo: $" + usuario.getSaldo());
            } else {
                out.println("Usuario no encontrado.");
            }
        } else {
            out.println("Error: Formato inválido.");
        }
    }

    private void handleTransferencia(String message, PrintWriter out) {
        String regex = "TRANSFERENCIA\\s+\"([\\p{L}\\p{M}\\s]+)\"\\s+\"([\\p{L}\\p{M}\\s]+)\"\\s+(\\d+(\\.\\d{1,2})?)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);
    
        System.out.println("Mensaje de transferencia recibido: " + message); // Línea de depuración
    
        if (matcher.matches()) {
            String nombreOrigen = matcher.group(1);
            String nombreDestino = matcher.group(2);
            double monto = Double.parseDouble(matcher.group(3));
            Usuario origen = buscarUsuario(nombreOrigen);
            Usuario destino = buscarUsuario(nombreDestino);
    
            if (origen != null && destino != null) {
                OperacionTransferencia operacionTransferencia = new OperacionTransferencia();
                operacionTransferencia.transferir(origen, destino, monto);
                try {
                    jsonUsuarioManager.guardarUsuarios(usuarios); // Actualizar JSON
                } catch (IOException e) {
                    System.err.println("Error al guardar usuarios en el archivo JSON.");
                    e.printStackTrace();
                }
                out.println("Transferencia realizada. Nuevo saldo de " + origen.getNombre() + ": $" + origen.getSaldo());
            } else {
                out.println("Usuario origen o destino no encontrado.");
            }
        } else {
            out.println("Error: Formato inválido.");
        }
    }

    private void handleDeposito(String message, PrintWriter out) {
        String regex = "DEPOSITO\\s+\"([\\p{L}\\p{M}\\s]+)\"\\s+(\\d+(\\.\\d{1,2})?)";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(message);

        if (matcher.matches()) {
            String nombre = matcher.group(1);
            double monto = Double.parseDouble(matcher.group(2));
            Usuario usuario = buscarUsuario(nombre);

            if (usuario != null) {
                OperacionDeposito operacionDeposito = new OperacionDeposito();
                operacionDeposito.depositar(usuario, monto);
                try {
                    jsonUsuarioManager.guardarUsuarios(usuarios); // Actualizar JSON
                } catch (IOException e) {
                    System.err.println("Error al guardar usuarios en el archivo JSON.");
                    e.printStackTrace();
                }
                out.println("Depósito realizado. Nuevo saldo: $" + usuario.getSaldo());
            } else {
                out.println("Usuario no encontrado.");
            }
        } else {
            out.println("Error: Formato inválido.");
        }
    }

    private Usuario autenticarUsuario(String nombre, String password) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    private Usuario buscarUsuario(String nombre) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombre().equalsIgnoreCase(nombre)) {
                return usuario;
            }
        }
        return null;
    }
}