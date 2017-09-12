import java.util.*;
public class Game {
    private static List<User> users = new ArrayList<>();

    public static void init(){
        boolean gameReady = false;
        try {
            int counter = 0;
            System.out.println("Waiting for users to join...");
            while (!gameReady) {
                Thread.sleep(500);
                System.out.println("0/20) users have joined.");
                String clientJoin = ServerUDP.recieveClientPackets();
                if(clientJoin.contains("ADD USER")) {
                    String[] messageElements = clientJoin.split(" ");
                    users.add(new User(messageElements[2], 2, true));
                }
                System.out.println("(" + counter + "/20) users have joined.");
                int userArrayLen = users.size();
                if(userArrayLen == 3){
                    counter++;
                    if(counter == 20){
                        gameReady = true;
                        System.out.println("Start Game Now.");
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
//    public static void
}
