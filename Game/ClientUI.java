import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ClientUI extends JFrame {

    private static String currentColour;
    public static Rectangle tempRect;
    private Rectangle rectangle = new Rectangle();
    private Rectangle coverRectangle = new Rectangle();

    private java.util.Timer timer = new Timer();

    public ClientUI() {
        setBackground(Color.lightGray);
        setSize(GridGame.theGrid[0].length * 10, GridGame.theGrid.length * 10);
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
            repaint();
        }
    };

    public static void recieveGameUpdates() {
        String recievedMessage = ClientUDP.recieveServerPackets();
        convertMessage(recievedMessage);
    }

    public static void convertMessage(String message) {
        if (message.contains("GRID UPDATE")) {
            message = message.substring(12);
            String[] parts = message.split("/");
            for (String i : parts) {
                String[] value = i.split("-");
                for (String j : value) {
                    String[] coordinates = j.split(",");
                    drawGame(coordinates);
                }
            }
        }
    }

    public static void drawGame(String[] coordinates) {
        int loopCount = 0;
        int rectX = Integer.parseInt(coordinates[1]);
        int rectY = Integer.parseInt(coordinates[2]);
        tempRect = new Rectangle(rectX, rectY, 10, 10);
        if(Integer.parseInt(coordinates[0]) <= 20) {
            currentColour = Game.users.get(Integer.parseInt(coordinates[0])).userColour;
        }
        else{
            currentColour = "blue";
        }
    }


    public void paint (Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (Objects.equals(currentColour, "blue")){
            g2.setColor(Color.blue);
            g2.fill(tempRect);
        }
        else{
            g2.setColor(Color.MAGENTA);
            g2.fill(tempRect);
        }
    }
}