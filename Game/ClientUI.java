import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class ClientUI extends JFrame{

    JPanel mainPanel = new JPanel();
    JPanel scorePanel = new JPanel();
    JPanel gamePanel = new JPanel();

    public static final String[] colours = { "Red", "Green", "Orange", "Blue", };

    public ClientUI(){
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
        mainPanel.add(scorePanel, BorderLayout.NORTH);
        mainPanel.add(new ClientGame(), BorderLayout.CENTER);
        mainPanel.setBackground(Color.lightGray);
        scorePanel.setBackground(Color.BLUE);
        gamePanel.add(new ClientGame());
        setSize(1000, 1300);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

        Scanner input = new Scanner(System.in);
        JOptionPane.showInputDialog(null, "Please enter your name:");
        String username = input.next();

        String inputValue = (String) JOptionPane.showInputDialog(null, "Choose a colour:",
                "Colour", JOptionPane.QUESTION_MESSAGE, null, // Use
                // default
                // icon
                colours, // Array of choices
                colours[1]); // Initial choice
    }
}
