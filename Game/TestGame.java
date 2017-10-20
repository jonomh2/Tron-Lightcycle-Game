import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.security.Key;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Scanner;

public class TestGame extends JFrame implements KeyListener{

    int secondsPassed = 0;


    private int xEdge = GridGame.theGrid[0].length*10;
    private int yEdge = GridGame.theGrid.length*10;
    private boolean lightWallOn = true;
    private Rectangle rectangle;
    private Rectangle rectangle2;
    private int currentDir = 0;
    public boolean gameFinish = true;
    private Timer timer = new Timer();
    private Scanner input = new Scanner(System.in);

    private TimerTask tsk = new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
            if (rectangle.x > 0 && rectangle.x < xEdge && rectangle.y > 0 && rectangle.y < yEdge){
                if (currentDir == 0){
                    rectangle2.setLocation(rectangle.x, rectangle.y);
                    rectangle.setLocation(rectangle.x - 10, rectangle.y);
                }
                else if (currentDir == 1){
                    rectangle2.setLocation(rectangle.x, rectangle.y);
                    rectangle.setLocation(rectangle.x, rectangle.y - 10);
                }
                else if (currentDir == 2){
                    rectangle2.setLocation(rectangle.x, rectangle.y);
                    rectangle.setLocation(rectangle.x + 10, rectangle.y);
                }
                else if (currentDir == 3){
                    rectangle2.setLocation(rectangle.x, rectangle.y);
                    rectangle.setLocation(rectangle.x, rectangle.y + 10);
                }
                repaint();
            }
            else{
                rectangle2.setLocation(rectangle.x, rectangle.y);
                rectangle.setLocation(480, 480);
                System.exit(0);
                repaint();
            }
        }
    };


    JPanel upperPanel1 = new JPanel();
    public TestGame() {
        this.addKeyListener(this);
        rectangle2 = new Rectangle(480, 480, 10, 10);
        rectangle = new Rectangle(480, 480, 10, 10);
        setSize(GridGame.theGrid[0].length*10,GridGame.theGrid.length*10);
        setBackground(Color.lightGray);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        timer.scheduleAtFixedRate(tsk, 0, 35);
    }

    @Override
    public void keyPressed(KeyEvent event){
        if (event.getKeyCode() == KeyEvent.VK_RIGHT){
            currentDir = 2;
        }
        else if (event.getKeyCode() == KeyEvent.VK_LEFT){
            currentDir = 0;
        }
        if (event.getKeyCode() == KeyEvent.VK_UP){
            currentDir = 1;
        }
        if (event.getKeyCode() == KeyEvent.VK_DOWN){
            currentDir = 3;
        }
        if (event.getKeyCode() == KeyEvent.VK_U){
            if (lightWallOn){
                lightWallOn = false;
            }
            else{
                lightWallOn = true;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event){

    }

    @Override
    public void keyTyped(KeyEvent event){

    }

    public void paint (Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (lightWallOn){
            g.setColor(Color.cyan);
            g2.fill(rectangle2);
        }
        else {
            g.setColor(Color.lightGray);
            g2.fill(rectangle2);
        }
        g.setColor(Color.darkGray);
        g2.fill(rectangle);
    }

    public static void main(String[] args) {
        new TestGame();
    }
}