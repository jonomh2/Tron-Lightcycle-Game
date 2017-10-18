import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

public class ClientUI extends JFrame {

    private static String currentColour;

    private Rectangle rectangle = new Rectangle();
    private Rectangle coverRectangle = new Rectangle();

    private java.util.Timer timer = new Timer();

    public ClientUI() {
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
            repaint();
            recieveGameUpdates();
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
                    String[] coords = j.split(",");
                    drawGame(coords);
                }
            }
        }
    }

    public static void drawGame(String[] coords) {
        int loopCount = 0;
        int rectX = Integer.parseInt(coords[1]);
        int rectY = Integer.parseInt(coords[2]);
        new Rectangle(rectX, rectY, 10, 10);
        if(Integer.parseInt(coords[0]) <= 20) {
            currentColour = Game.users.get(Integer.parseInt(coords[0])).userColour;
        }
        else{
            currentColour = "blue";
        }
    }


    public void paint (Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

    }
}