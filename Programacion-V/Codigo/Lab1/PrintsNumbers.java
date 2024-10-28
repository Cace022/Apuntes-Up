public class PrintsNumbers implements Runnable {// Atributo de la clase
    
    // Variable de instancia
    private boolean keepgoing = true;

    // Metodo que detiene la impresion
    public void stopPrinting()
    {
        // Cambiar el valor de la variable
        keepgoing = false;
    } 

    // Metodo run
    public void run ()
    {   
        // Variable local
        int i = 1;
        // Bucle while
        while (keepgoing)
        {
            // Imprimir el valor de i
            System.out.println(i++);
            try
            {
                // Esperar 1000 milisegundos
                Thread.sleep(1000);
            }
            // Capturar la excepcion
            catch (InterruptedException e)
            {
                System.out.println("Error");
            }
        }

    }
}

