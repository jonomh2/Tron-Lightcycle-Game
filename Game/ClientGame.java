import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class ClientGame extends JPanel {

    public static int[][] clientGrid = new int[101][101];


    public static Rectangle tempRect;

    private java.util.Timer timer = new Timer();

    public ClientGame() {
        setVisible(true);
        setBackground(new Color(25, 25,25));
        setPreferredSize(new Dimension(1000, 1000));
        timer.scheduleAtFixedRate(tsk, 0, 20);
        timer.scheduleAtFixedRate(repaintGrid, 10, 10);

    }

    private TimerTask tsk = new TimerTask() {
        @Override
        public void run() {
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
        //fix this
        int[] intMessage = new int[3];
        if (message.contains("GRID UPDATE")) {
            System.out.println(message);
            message = message.substring(12);
            String[] parts = message.split("/");
            for (String i : parts) {
                String[] coordinates = i.split("-");
                for (int g = 0; g < coordinates.length; g++) {
                    intMessage[g] = Integer.parseInt(coordinates[g]);
                }
                drawGame(intMessage);
            }
        }
        else if (message.contains("GAME OVER")){
            for (int row = 0; row < clientGrid.length; row++) {
                for (int columnInt = 0; columnInt < clientGrid[row].length; columnInt++) {
                    clientGrid[row][columnInt] = 0;
                }
            }
            JOptionPane.showMessageDialog(null, "Game over, thanks for playing. Press OK to quit.");
            System.exit(0);
        }
    }

    public static void drawGame(int[] coordinates) {
        System.out.println(coordinates[0] + " is at " + coordinates[1] + ", " + coordinates[2]);
        clientGrid[coordinates[1]][coordinates[2]] = coordinates[0];
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g;
            for (int row = 0; row < clientGrid.length; row++) {
                for (int columnInt = 0; columnInt < clientGrid[row].length; columnInt++) {

                    if (clientGrid[row][columnInt] != 0) {

                        if (clientGrid[row][columnInt] < 21) {
                            tempRect = new Rectangle(columnInt * 10, row * 10, 10, 10);
                            g2.setColor(Color.blue);
                            g2.fill(tempRect);

                        } else if (clientGrid[row][columnInt] > 30 && clientGrid[row][columnInt] < 51) {
                            tempRect = new Rectangle(columnInt * 10, row * 10, 10, 10);
                            g2.setColor(Color.cyan);
                            g2.fill(tempRect);

                        } else {
                            tempRect = new Rectangle(columnInt * 10, row * 10, 10, 10);
                            g2.setColor(Color.magenta);
                            g2.fill(tempRect);
                        }
                    }
                }
            }
        }
    }