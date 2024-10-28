/**
 * @(#)Producer.java
 *
 *
 * @author 
 * @version 1.00 2007/12/19
 */


 public class Producer implements Runnable {
    private SyncStack theStack;
    private int num; // Para llevar la cuenta de los elementos producidos

    public Producer(SyncStack theStack) {
        this.theStack = theStack;
    }

    public void run() {
        char c;
        for (int i = 0; i < 10; i++) {
            c = (char) (Math.random() * 26 + 'A');
            theStack.push(c); 
            num++;
            // Aquí se debe agregar la salida al JTextArea en lugar de System.out
            // Puedes hacerlo a través de un método en SyncStack o en el constructor
            // de la GUI
            System.out.println("Productor " + num + "; " + c);

            try {
                Thread.sleep((int) (Math.random() * 300));
            } catch (InterruptedException e) {
                System.out.println("Se produjo una InterruptedException");
            }
        }
    }
}
