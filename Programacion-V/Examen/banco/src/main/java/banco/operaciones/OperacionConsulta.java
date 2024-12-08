package banco.operaciones;

import banco.Usuario;

public class OperacionConsulta {
    public void consultarSaldo(Usuario usuario) {
        System.out.println("El saldo actual de " + usuario.getNombre() + " es: $" + usuario.getSaldo());
    }

    public void consultarUsuario(Usuario usuario) {
        System.out.println("Detalles del Usuario:");
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nombre: " + usuario.getNombre());
        System.out.println("Saldo: $" + usuario.getSaldo());
        System.out.println("Tipo: " + usuario.getTipo());
    }
}
