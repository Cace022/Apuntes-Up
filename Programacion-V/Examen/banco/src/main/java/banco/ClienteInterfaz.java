package banco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClienteInterfaz {

    private static Socket socket;
    private static PrintWriter out;
    private static BufferedReader in;
    private static JTextArea mensajeArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Banco Digital");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());

        // Panel de login
        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(240, 248, 255));
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        JLabel tituloLabel = new JLabel("Bienvenido al Banco Digital", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 20));
        tituloLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(tituloLabel);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel nombreLabel = new JLabel("Nombre de Usuario");
        JTextField nombreField = new JTextField(20);
        nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(nombreLabel);
        loginPanel.add(nombreField);

        JLabel passLabel = new JLabel("Contraseña");
        JPasswordField passwordField = new JPasswordField(20);
        passLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(passLabel);
        loginPanel.add(passwordField);
        loginPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginPanel.add(loginButton);

        panelPrincipal.add(loginPanel, "login");

        // Panel Principal después de login
        JPanel homePanel = new JPanel();
        homePanel.setBackground(new Color(255, 255, 255));
        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));

        JLabel saludoLabel = new JLabel("Hola, usuario!");
        saludoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        saludoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        homePanel.add(saludoLabel);

        JLabel saldoLabel = new JLabel("Saldo Actual: $0.00");
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        homePanel.add(saldoLabel);

        JButton consultarSaldoButton = new JButton("Consultar Saldo");
        consultarSaldoButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homePanel.add(consultarSaldoButton);

        JButton depositarButton = new JButton("Depositar");
        depositarButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homePanel.add(depositarButton);

        JButton transferirButton = new JButton("Transferir");
        transferirButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        homePanel.add(transferirButton);

        mensajeArea = new JTextArea(10, 40);
        mensajeArea.setEditable(false);
        homePanel.add(new JScrollPane(mensajeArea));

        panelPrincipal.add(homePanel, "home");

        // Acción del botón de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String password = new String(passwordField.getPassword());
                try {
                    socket = new Socket("127.0.0.1", 9999);
                    out = new PrintWriter(socket.getOutputStream(), true);
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                    // Enviar mensaje de login
                    out.println("LOGIN " + nombre + " " + password);
                    String response = in.readLine();
                    mensajeArea.setText(response);
                    if (response.contains("Autenticación exitosa")) {
                        CardLayout cl = (CardLayout) (panelPrincipal.getLayout());
                        cl.show(panelPrincipal, "home");
                    }

                } catch (IOException ex) {
                    mensajeArea.setText("Error al conectar con el servidor.");
                }
            }
        });

        // Acciones para las operaciones
        consultarSaldoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out.println("CONSULTAR_SALDO");
                try {
                    String response = in.readLine();
                    saldoLabel.setText("Saldo Actual: " + response);
                } catch (IOException ex) {
                    mensajeArea.setText("Error al consultar saldo.");
                }
            }
        });

        depositarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String monto = JOptionPane.showInputDialog(frame, "Ingrese monto a depositar:");
                if (monto != null) {
                    out.println("DEPOSITAR " + monto);
                    try {
                        String response = in.readLine();
                        mensajeArea.setText(response);
                    } catch (IOException ex) {
                        mensajeArea.setText("Error al realizar el depósito.");
                    }
                }
            }
        });

        transferirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String monto = JOptionPane.showInputDialog(frame, "Ingrese monto a transferir:");
                String destino = JOptionPane.showInputDialog(frame, "Ingrese nombre del destino:");
                if (monto != null && destino != null) {
                    out.println("TRANSFERIR " + monto + " " + destino);
                    try {
                        String response = in.readLine();
                        mensajeArea.setText(response);
                    } catch (IOException ex) {
                        mensajeArea.setText("Error al realizar la transferencia.");
                    }
                }
            }
        });

        frame.add(panelPrincipal);
        frame.setVisible(true);
    }
}
