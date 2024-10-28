/**
 * @(#)Consumer.java
 *
 *
 * @author 
 * @version 1.00 2007/12/19
 */


 public class Consumer implements Runnable {
    private SyncStack theStack; // Referencia al SyncStack
    private int num; // Para llevar la cuenta de los elementos consumidos

    public Consumer(SyncStack theStack) {
        this.theStack = theStack;
    }

    public void run() {
        char c;
        for (int i = 0; i < 10; i++) {
            c = theStack.pop(); // Consume un elemento del stack
            num++;
            // Aquí se debe agregar la salida al JTextArea en lugar de System.out
            System.out.println("Consumidor " + num + "; " + c);

            try {
                Thread.sleep((int) (Math.random() * 300));
            } catch (InterruptedException e) {
                System.out.println("Se produjo una InterruptedException");
            }
        }
    }
}
