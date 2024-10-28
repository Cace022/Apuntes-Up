import java.util.Scanner;// Importar la clase Scanner

// Clase que implementa la interfaz Runnable
public class Print {
    // Main method
   
    public static void main(String[] args){
   
        // Crear un objeto Scanner
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a number: ");
        int n = sc.nextInt();
   
        // Cerrar el Scanner
        sc.close();
   
        // Crear un objeto de la clase PrintsNumbers
        PrintsNumbers p = new PrintsNumbers();
   
        // Crear un objeto de la clase Thread
        Thread t = new Thread(p);
   
        // Iniciar el hilo
        t.start();
   
        // Esperar n milisegundos
        try
        {
            Thread.sleep(n*10000);
        }
   
        // Capturar la excepcion
        catch (InterruptedException e)
        {
            System.out.println("Error");
        }
   
        // Detener el hilo
        p.stopPrinting();
        

        System.out.println("main() is ending…");
    }
}
