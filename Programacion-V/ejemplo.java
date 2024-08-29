public class ejemplo implements Runnable 
{
    // Se crea el atributo mensaje
    private String mensaje;

    // Se crea el constructor de la clase ejemplo 
    public ejemplo(String mensaje) 
    {
        this.mensaje = mensaje;
    }

    // Se crea la clase run que es la que se ejecuta cuando se inicia el hilo
    public void run ()
    {
        while (true)
        {
            System.out.println(mensaje);
        }
    }
    
    public static void main (String[] args)
    {
       // Crear un objeto de la clase ejemplo
       ejemplo e = new ejemplo ("Hola"); 
       Thread t = new Thread(e);

       // Esto es equivalente  mi run () ya que este inicia el hilo 
       t.start();   
    }


    

    
}
