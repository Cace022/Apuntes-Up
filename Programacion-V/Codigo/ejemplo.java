public class ejemplo implements Runnable 
{
    private String mensaje;

    public ejemplo(String mensaje) 
    {
        this.mensaje = mensaje;
    }

    public void run ()
    {
        while (true)
        {
            System.out.println(mensaje);
        }
    }
    
    public static void main (String[] args)
    {
       ejemplo e = new ejemplo ("Hola"); 
       Thread t = new Thread(e);
       t.start();
    }


    

    
}
