import com.sun.xml.internal.bind.v2.TODO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import static javax.swing.Box.createVerticalGlue;

public class UserInput extends JFrame implements ActionListener{

    String[] userColours = {"Red", "White", "Pink", "Orange", "Green", "Purple", "Yellow", "Cyan", "Blue", "Black", "Grey", "Brown", "Navy", "Dark Green", "Coral", "Maroon", "Baby Blue", "Light Green", "Dark Purple", "Pale Orange"};

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

    public void actionPerformed(ActionEvent e){
        String userName = nameText.getText();
        String colour = (String)colourChoice.getSelectedItem();

        if (!Objects.equals(userName, "")){

            try {
                ClientUDP.sendPackets("ADD USER " + userName);
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
