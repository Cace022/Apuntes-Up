package banco.operaciones;

import banco.Usuario;

public class OperacionDeposito {
    public void depositar(Usuario usuario, double monto) {
        if (monto > 0) {
            usuario.setSaldo(usuario.getSaldo() + monto);
            System.out.println("Depósito realizado exitosamente. Nuevo saldo: $" + usuario.getSaldo());
        } else {
            System.out.println("El monto debe ser positivo.");
        }
    }
}
