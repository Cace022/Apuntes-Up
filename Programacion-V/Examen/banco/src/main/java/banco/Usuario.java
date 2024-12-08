package banco;

public class Usuario {
    private int id;
    private String nombre;
    private String password;
    private double saldo;
    private String tipo; // normal o admin

    // Constructor
    public Usuario(int id, String nombre,String password, double saldo, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.saldo = saldo;
        this.tipo = tipo;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
