import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ClientUI extends JFrame{

    JPanel mainPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel gamePanel = new JPanel();

    public ClientUI(){
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        mainPanel.setBackground(Color.lightGray);
        scorePanel.setBackground(Color.BLUE);
        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(new ClientGame(), BorderLayout.CENTER);
        gamePanel.add(new ClientGame());
        setSize(1000, 1300);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        new UserInput();
    }


}
