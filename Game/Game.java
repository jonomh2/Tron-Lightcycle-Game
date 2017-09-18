import java.util.*;
public class Game {
    private static List<User> users = new ArrayList<>();

    public static void init(){
        boolean gameReady = false;
        try {
            int counter = 0;
            System.out.println("Waiting for users to join...");
            System.out.println("(0/20) users have joined.");

            while (!gameReady) {
                String clientJoin = ServerUDP.recieveClientPackets();

                if(clientJoin.contains("ADD USER")) {
                    String userName = clientJoin.substring(8);
                    users.add(new User(userName, 2, true));
                }
                System.out.println("Welcome " + users.get(users.size()));
                System.out.println("(" + users.size() + "/20) users have joined.");

                if(users.size() <= 3 && users.size() < 20){
                    counter++;
                    if(counter == 20){
                        gameReady = true;
                        System.out.println("Start Game Now.");
                    }
                }
                if (users.size() >= 20){
                    gameReady = true;
                    System.out.println("Start Game Now.");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
//    public static void
}


