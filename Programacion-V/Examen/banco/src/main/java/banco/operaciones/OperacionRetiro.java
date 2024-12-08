package banco.operaciones;

import banco.Usuario;

public class OperacionRetiro {
    public void retirar(Usuario usuario, double monto) {
        if (monto > 0 && usuario.getSaldo() >= monto) {
            usuario.setSaldo(usuario.getSaldo() - monto);
            System.out.println("Retiro realizado exitosamente. Nuevo saldo: $" + usuario.getSaldo());
        } else {
            System.out.println("Monto inválido o saldo insuficiente.");
        }
    }
}

