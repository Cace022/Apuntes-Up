import java.util.TimerTask;

public class Remainder extends TimerTask{
    String message;
    public Remainder(String message){
        this.message = message;
    }
    @Override
    public void run() {
        System.out.println(message);
    }
    
}