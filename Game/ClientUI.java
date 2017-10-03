import javax.swing.*;

public class ClientUI {
    public static void main(String[] args) {
        JFrame newWindow = new JFrame("New window");
        newWindow.setSize(1000, 1000);
        newWindow.setTitle("My frame");
        newWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        newWindow.setVisible(true);
    }
}
