import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static javax.swing.Box.createVerticalGlue;

public class UserInput extends JFrame implements ActionListener{

    String[] userColours = {"Red", "White", "Pink", "Orange", "Green", "Purple", "Yellow", "Cyan", "Blue", "Black", "Grey", "Brown", "Navy", "Dark Green", "Coral", "Maroon", "Light Purple", "Light Green", "Dark Purple", "Pale Orange"};

    JPanel mainPanel = new JPanel();
    JComboBox colourChoice = new JComboBox(userColours);
    JTextField nameText = new JTextField();
    JLabel colourLabel = new JLabel("Player colour: ");
    JLabel nameLabel = new JLabel("Username: ");
    JButton confirmButton = new JButton("Ok");

    public UserInput(){
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        setSize(300, 200);
        this.setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        add(mainPanel);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(nameLabel);
        nameText.setSize(100, 50);
        mainPanel.add(nameText);
        mainPanel.add(Box.createRigidArea(new Dimension(0,25)));
        mainPanel.add(colourLabel);
        mainPanel.add(colourChoice);
        mainPanel.add(Box.createRigidArea(new Dimension(0,25)));
        confirmButton.addActionListener(this);
        mainPanel.add(confirmButton);
    }

    private Color getUserColour(String colour){
        if (Objects.equals(colour, "Red")){
            return new Color(255, 0, 0);
        }
        else if (Objects.equals(colour, "White")){
            return new Color(255, 255, 255);
        }
        else if (Objects.equals(colour, "Pink")){
            return new Color(255, 89, 213);
        }
        else if (Objects.equals(colour, "Orange")){
            return new Color(255, 97, 0);
        }
        else if (Objects.equals(colour, "Green")){
            return new Color(12, 209, 25);
        }
        else if (Objects.equals(colour, "Purple")){
            return new Color(143, 11, 209);
        }
        else if (Objects.equals(colour, "Yellow")){
            return new Color(250, 255, 0);
        }
        else if (Objects.equals(colour, "Cyan")){
            return new Color(0, 237, 255);
        }
        else if (Objects.equals(colour, "Blue")){
            return new Color(0, 89, 255);
        }
        else if (Objects.equals(colour, "Black")){
            return new Color(0, 0, 0);
        }
        else if (Objects.equals(colour, "Grey")){
            return new Color(117, 117, 117);
        }
        else if (Objects.equals(colour, "Brown")){
            return new Color(61, 31, 20);
        }
        else if (Objects.equals(colour, "Navy")){
            return new Color(1, 0, 68);
        }
        else if (Objects.equals(colour, "Dark Green")){
            return new Color(5, 51, 0);
        }
        else if (Objects.equals(colour, "Coral")){
            return new Color(191, 42, 76);
        }
        else if (Objects.equals(colour, "Maroon")){
            return new Color(109, 4, 4);
        }
        else if (Objects.equals(colour, "Light Purple")){
            return new Color(176, 111, 237);
        }
        else if (Objects.equals(colour, "Light Green")){
            return new Color(166, 255, 163);
        }
        else if (Objects.equals(colour, "Dark Purple")){
            return new Color(34, 0, 61);
        }
        else if (Objects.equals(colour, "Pale Orange")){
            return new Color(255, 178, 145);
        }
        else return Color.blue;
    }

    public void actionPerformed(ActionEvent e){
        String userName = nameText.getText();
        String colour = (String)colourChoice.getSelectedItem();

        if (!Objects.equals(userName, "")){

            try {
                ClientGame.playerName = userName;
                ClientUDP.sendPackets("ADD USER " + userName);
                ClientGame.playerColour = getUserColour(colour);
                setVisible(false);
        }

            catch (Exception e1) {
            e1.printStackTrace();
        }
        }

        else{
            JOptionPane.showMessageDialog(null, "Please enter a username.");
        }
    }



}
