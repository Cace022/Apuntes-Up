package banco;

import com.formdev.flatlaf.FlatLightLaf;
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
    private static JTextField inputField;
    private static String operacionActual = "";
    private static String destinatario = "";

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        JFrame frame = new JFrame("Banco Digital");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new CardLayout());

        // Panel de login con gradiente
        JPanel loginPanel = new GradientPanel();
        loginPanel.setLayout(new GridBagLayout());

        // Contenedor blanco centrado con esquinas redondeadas
        RoundedPanel whitePanel = new RoundedPanel(30);
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setLayout(new GridBagLayout());
        whitePanel.setPreferredSize(new Dimension(400, 350));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel tituloLabel = new JLabel("Bienvenido al Banco Digital", JLabel.CENTER);
        tituloLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        whitePanel.add(tituloLabel, gbc);

        JLabel nombreLabel = new JLabel("Nombre de Usuario:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        whitePanel.add(nombreLabel, gbc);

        JTextField nombreField = new JTextField(15);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        whitePanel.add(nombreField, gbc);

        JLabel passLabel = new JLabel("Contraseña:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        whitePanel.add(passLabel, gbc);

        JPasswordField passwordField = new JPasswordField(15);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        whitePanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Iniciar Sesión");
        loginButton.setBackground(new Color(0, 123, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        whitePanel.add(loginButton, gbc);

        // Panel Blanco centrado
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        loginPanel.add(whitePanel, gbc);

        panelPrincipal.add(loginPanel, "login");

        // Home panel
        JPanel homePanel = new JPanel(new BorderLayout(10, 10));
        homePanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel saludoLabel = new JLabel("Hola, usuario!");
        saludoLabel.setFont(new Font("Arial", Font.BOLD, 20));
        saludoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(saludoLabel);

        JLabel saldoLabel = new JLabel("Saldo Actual: $0.00");
        saldoLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        saldoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        headerPanel.add(saldoLabel);

        homePanel.add(headerPanel, BorderLayout.NORTH);

        JPanel operationsPanel = new JPanel(new GridLayout(4, 1, 10, 10));
        operationsPanel.setBackground(Color.WHITE);

        JButton consultarSaldoButton = new JButton("Consultar Saldo");
        JButton retirarButton = new JButton("Retirar");
        JButton depositarButton = new JButton("Depositar");
        JButton transferirButton = new JButton("Transferir");

        operationsPanel.add(consultarSaldoButton);
        operationsPanel.add(retirarButton);
        operationsPanel.add(depositarButton);
        operationsPanel.add(transferirButton);

        homePanel.add(operationsPanel, BorderLayout.CENTER);

        JPanel keypadPanel = new JPanel(new BorderLayout());
        keypadPanel.setBackground(new Color(240, 240, 240));
        keypadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputField = new JTextField();
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setFont(new Font("Arial", Font.BOLD, 18));
        inputField.setEditable(false);

        keypadPanel.add(inputField, BorderLayout.NORTH);

        JPanel buttonsPanel = new JPanel(new GridLayout(4, 3, 5, 5));
        for (int i = 1; i <= 9; i++) {
            JButton button = new JButton(String.valueOf(i));
            button.setFont(new Font("Arial", Font.BOLD, 16));
            button.addActionListener(e -> inputField.setText(inputField.getText() + button.getText()));
            button.setEnabled(false); // Inicialmente deshabilitado
            buttonsPanel.add(button);
        }

        JButton zeroButton = new JButton("0");
        zeroButton.setFont(new Font("Arial", Font.BOLD, 16));
        zeroButton.addActionListener(e -> inputField.setText(inputField.getText() + "0"));
        zeroButton.setEnabled(false); // Inicialmente deshabilitado
        buttonsPanel.add(zeroButton);

        JButton clearButton = new JButton("C");
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setBackground(Color.RED);
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(e -> inputField.setText(""));
        clearButton.setEnabled(false); // Inicialmente deshabilitado
        buttonsPanel.add(clearButton);

        JButton enterButton = new JButton("OK");
        enterButton.setFont(new Font("Arial", Font.BOLD, 16));
        enterButton.setBackground(Color.GREEN);
        enterButton.setForeground(Color.WHITE);
        enterButton.setEnabled(false); // Inicialmente deshabilitado
        buttonsPanel.add(enterButton);

        keypadPanel.add(buttonsPanel, BorderLayout.CENTER);
        homePanel.add(keypadPanel, BorderLayout.EAST);

        mensajeArea = new JTextArea(10, 40);
        mensajeArea.setEditable(false);
        homePanel.add(new JScrollPane(mensajeArea), BorderLayout.SOUTH);

        panelPrincipal.add(homePanel, "home");

        frame.add(panelPrincipal);
        frame.setVisible(true);

        // Conectar al servidor
        try {
            socket = new Socket("127.0.0.1", 9999);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Acción del botón de login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nombre = nombreField.getText();
                String password = new String(passwordField.getPassword());
                out.println("LOGIN \"" + nombre + "\" " + password);
                try {
                    String response = in.readLine();
                    mensajeArea.setText(response);
                    if (response.contains("Autenticación exitosa")) {
                        CardLayout cl = (CardLayout) panelPrincipal.getLayout();
                        cl.show(panelPrincipal, "home");
                        saludoLabel.setText("Hola, " + nombre + "!");
                    } else {
                        JOptionPane.showMessageDialog(frame, response, "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        // Método para habilitar/deshabilitar teclado
        ActionListener enableKeyboard = e -> {
            for (Component comp : buttonsPanel.getComponents()) {
                if (comp instanceof JButton) {
                    comp.setEnabled(true);
                }
            }
        };

        ActionListener disableKeyboard = e -> {
            for (Component comp : buttonsPanel.getComponents()) {
                if (comp instanceof JButton) {
                    comp.setEnabled(false);
                }
            }
        };

        // Configurar botones para habilitar el teclado
        depositarButton.addActionListener(e -> {
            operacionActual = "DEPOSITO";
            inputField.setText("");
            mensajeArea.append("Ingrese el monto para depositar.\n");
            enableKeyboard.actionPerformed(e);
        });

        retirarButton.addActionListener(e -> {
            operacionActual = "RETIRO";
            inputField.setText("");
            mensajeArea.append("Ingrese el monto para retirar.\n");
            enableKeyboard.actionPerformed(e);
        });

        transferirButton.addActionListener(e -> {
            operacionActual = "TRANSFERENCIA";
            destinatario = JOptionPane.showInputDialog(frame, "Ingrese el nombre del destinatario:");
            inputField.setText("");
            mensajeArea.append("Ingrese el monto para transferir.\n");
            enableKeyboard.actionPerformed(e);
        });

        // "OK" deshabilita el teclado nuevamente y realiza la operación actual
        enterButton.addActionListener(e -> {
            disableKeyboard.actionPerformed(e);
            String monto = inputField.getText();
            String nombre = nombreField.getText();
            switch (operacionActual) {
                case "DEPOSITO":
                    out.println("DEPOSITO \"" + nombre + "\" " + monto);
                    break;
                case "RETIRO":
                    out.println("RETIRO \"" + nombre + "\" " + monto);
                    break;
                case "TRANSFERENCIA":
                    out.println("TRANSFERENCIA \"" + nombre + "\" \"" + destinatario + "\" " + monto);
                    break;
                default:
                    mensajeArea.append("Operación no reconocida.\n");
                    break;
            }
            try {
                String response = in.readLine();
                mensajeArea.append(response + "\n");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            inputField.setText(""); // Limpia el campo tras procesar
            operacionActual = ""; // Resetea la operación actual
        });

        // Acciones para botones
        consultarSaldoButton.addActionListener(e -> {
            out.println("CONSULTA \"" + nombreField.getText() + "\"");
            try {
                String response = in.readLine();
                saldoLabel.setText("Saldo Actual: " + response);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    // Clases personalizadas para diseños que no están nativamente soportados por Swing

    // Gradiente
    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Color color1 = new Color(135, 206, 250);
            Color color2 = new Color(0, 123, 255);
            GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // Esquinas Redondeadas
    static class RoundedPanel extends JPanel {
        private int cornerRadius;

        public RoundedPanel(int cornerRadius) {
            super();
            this.cornerRadius = cornerRadius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fondo blanco con esquinas redondeadas
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);

            // Borde de color claro con esquinas redondeadas
            g2d.setColor(Color.LIGHT_GRAY);
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

            g2d.dispose();
        }
    }
}