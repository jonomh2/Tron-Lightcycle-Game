import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ClientUI extends JFrame{

    private Rectangle rectangle = new Rectangle();
    private Rectangle coverRectangle = new Rectangle();

    private java.util.Timer timer = new Timer();

    public ClientUI() {
        setSize(GridGame.theGrid[0].length*10,GridGame.theGrid.length*10);
        setBackground(Color.lightGray);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        timer.scheduleAtFixedRate(tsk, 0, 35);
    }

    private int secondsPassed = 0;

    private TimerTask tsk = new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
            recieveGameUpdates();
        }
    };

    public static void recieveGameUpdates(){
        String recievedMessage = ClientUDP.recieveServerPackets();
        convertMessage(recievedMessage);
    }

    public static void convertMessage(String message){
        if (message.contains("GRID UPDATE")){
            message = message.substring(12);
            String[] parts = message.split("/");
            for (String i : parts){
                String[] value = i.split("-");
                for (String j : value){
                    String[] coords = j.split(",");
                    drawGame(coords);
                    }
                }
            }
        }

    public static void drawGame(String[] coords) {
        int loopCount = 0;

//        coverRectangle = new Rectangle(480, 480, 10, 10);
//        rectangle = new Rectangle(480, 480, 10, 10);
        for (String value : coords){
            int currentNum = Integer.parseInt(value);
            if (loopCount == 0){

            }
    }
}


//    public void paint (Graphics g) {
//        Graphics2D g2 = (Graphics2D)g;
//        if (lightWallOn){
//            g.setColor(Color.cyan);
//            g2.fill(rectangle2);
//        }
//        else {
//            g.setColor(Color.lightGray);
//            g2.fill(rectangle2);
//        }
//        g.setColor(Color.darkGray);
//        g2.fill(rectangle);
//    }
}