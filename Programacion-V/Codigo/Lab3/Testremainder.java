import java.util.Timer;

public class Testremainder {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new Remainder("Hello World"),0);// al instante 
        timer.schedule(new Remainder("Goodbye World"),2000);// 2 segundos despues del primero
        timer.schedule(new Remainder("Hello Again"),3000);// 3 segundos depues del segundo
    }
}
