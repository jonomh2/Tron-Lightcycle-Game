import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Scanner;
import java.util.*;
import java.util.Timer;

public class ClientUI extends JFrame implements KeyListener{

    boolean gameReady = false;
    String serverMessage;
    String clientMessage;
    private Timer timer = new Timer();

    TimerTask sendPackets = new TimerTask() {
        @Override
        public void run() {
            try {
                ClientUDP.sendPackets("");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    TimerTask recievePackets = new TimerTask() {
        @Override
        public void run() {
            serverMessage = ClientUDP.recieveServerPackets();
            if(Objects.equals(serverMessage, "GAME START")){
                gameReady = true;
            }
        }
    };

    TimerTask paintGrid = new TimerTask() {
        @Override
        public void run() {
            gamePanel.repaint();
        }
    };

    static int UserID = 0;

    JPanel mainPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel gamePanel = new JPanel();

    public ClientUI() {
        setVisible(true);
        this.addKeyListener(this);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        scorePanel.setBackground(Color.BLUE);
        mainPanel.add(scorePanel, BorderLayout.NORTH);
        gamePanel.add(new ClientGame());
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        setSize(1000, 1300);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        new UserInput();
        getUserID();
        timer.scheduleAtFixedRate(sendPackets, 20, 30);
        repaint();
    }

    public static void getUserID() {
        while (UserID == 0) {
            String message = ClientUDP.recieveServerPackets();
            if (message.contains("USERID")) {
                message = message.substring(7);
                UserID = Integer.parseInt(message);
                System.out.println(UserID);
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        clientMessage = "";
        if (event.getKeyCode() == KeyEvent.VK_LEFT){
            clientMessage = "USER " + UserID + " DIRECTION " + 0;
            System.out.println("left");
        }
        else if (event.getKeyCode() == KeyEvent.VK_RIGHT){
            clientMessage = "USER " + UserID + " DIRECTION " + 2;
            System.out.println("right");
        }
        else if (event.getKeyCode() == KeyEvent.VK_UP){
            clientMessage = "USER " + UserID + " DIRECTION " + 1;
            System.out.println("up");
        }
        else if (event.getKeyCode() == KeyEvent.VK_DOWN){
            clientMessage = "USER " + UserID + " DIRECTION " + 3;
            System.out.println("down");
        }
        else if (event.getKeyCode() == KeyEvent.VK_S){

            clientMessage = "USER " + UserID + " LIGHTWALLTOGGLE";
        }
        else if (event.getKeyCode() == KeyEvent.VK_A){

            clientMessage = "USER " + UserID + " SPEEDUPDATE SLOW";
        }
        else if (event.getKeyCode() == KeyEvent.VK_D){

            clientMessage = "USER " + UserID + " SPEEDUPDATE SPEEDUP";
        }
        try {
            ClientUDP.sendPackets(clientMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
