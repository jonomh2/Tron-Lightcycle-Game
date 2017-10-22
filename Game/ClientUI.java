import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ClientUI extends JFrame {

    public static int[][] clientGrid = new int[101][101];


    JPanel mainPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel gamePanel = new JPanel();
    private static String currentColour;
    public static Rectangle tempRect;
    private Rectangle rectangle = new Rectangle();
    private Rectangle coverRectangle = new Rectangle();

    private java.util.Timer timer = new Timer();

    public ClientUI() {
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        mainPanel.setBackground(Color.lightGray);
        setSize(GridGame.theGrid[0].length * 10, GridGame.theGrid.length * 10);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        Game.users.add(new User("User 20", 1, 2, 0, true, "blue"));
        timer.scheduleAtFixedRate(tsk, 0, 100);
        timer.scheduleAtFixedRate(repaintGrid, 0, 50);

    }

    private static boolean rectanglePlaced = false;

    private int secondsPassed = 0;

    private TimerTask tsk = new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
            receiveGameUpdates();
        }
    };

    private TimerTask repaintGrid = new TimerTask() {
        @Override
        public void run() {
            repaint();
        }
    };

    public static void receiveGameUpdates() {
        String recievedMessage = ClientUDP.recieveServerPackets();
        convertMessage(recievedMessage);
    }

    public static void convertMessage(String message) {
        int[] intMessage = new int[3];
        if (message.contains("GRID UPDATE")) {
            message = message.substring(12);
            String[] parts = message.split("/");
            for (String i : parts) {
                String[] coordinates = i.split("-");
                for (int g = 0; g < coordinates.length; g++) {
                    try {
                        System.out.println("before assignment:" + intMessage[g]);
                        intMessage[g] = Integer.parseInt(coordinates[g]);
                        System.out.println("after assignment:" + intMessage[g]);
                    }
                    catch (Exception e){
                        System.out.println("error1");
                        System.out.println("error num:" + intMessage[g]);
                        e.printStackTrace();
                    }
                }
                drawGame(intMessage);
            }
        }
    }

    public static void drawGame(int[] coordinates) {
        clientGrid[coordinates[1]][coordinates[2]] = coordinates[0];
    }


    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for (int row = 0; row < clientGrid.length; row++) {
            for (int columnInt = 0; columnInt < clientGrid[row].length; columnInt++) {
                if (clientGrid[row][columnInt] != 0) {
                    if (clientGrid[row][columnInt] < 21) {
                        tempRect = new Rectangle(row, columnInt, 10, 10);
                        g2.setColor(Color.blue);
                        g2.fill(tempRect);
                    }
                }
            }
        }
//        if (Objects.equals(currentColour, "blue")){
//            g2.setColor(Color.blue);
//            g2.fill(tempRect);
//        }
//        else{
//            g2.setColor(Color.MAGENTA);
//            g2.fill(tempRect);
//        }
    }
}
