package banco.operaciones;

import banco.Usuario;

public class OperacionTransferencia {
    public void transferir(Usuario origen, Usuario destino, double monto) {
        if (monto > 0 && origen.getSaldo() >= monto) {
            origen.setSaldo(origen.getSaldo() - monto);
            destino.setSaldo(destino.getSaldo() + monto);
            System.out.println("Transferencia realizada exitosamente.");
            System.out.println("Nuevo saldo de " + origen.getNombre() + ": $" + origen.getSaldo());
            System.out.println("Nuevo saldo de " + destino.getNombre() + ": $" + destino.getSaldo());
        } else {
            System.out.println("Monto inválido o saldo insuficiente en la cuenta origen.");
        }
    }
}
