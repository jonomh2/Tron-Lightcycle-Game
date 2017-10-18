import java.util.*;
public class Game {
    public static List<User> users = new ArrayList<>();

    private static int secondsPassed = 0;
    private static boolean timeUp = false;
    private static boolean newPlayerJoin = true;

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        public void run() {
            secondsPassed++;
            if (secondsPassed == 10){
                System.out.println("Time's up.");
                timeUp = true;
            }
        }
    };

    public void start(){
        timer.scheduleAtFixedRate(task, 1000, 1000);
    }

    public static void init(){
        boolean gameReady = false;
        try {
            int IDCount = 0;
            System.out.println("Waiting for users to join...");
            System.out.println("(0/20) users have joined.");
            while (!gameReady) {
                String clientJoin = ServerUDP.recieveClientPackets();

                if(clientJoin.contains("ADD USER")) {
                    IDCount++;
                    String userName = clientJoin.substring(9);
                    if (users.size() >= 3){
                        secondsPassed = 0;
                    }
                    users.add(new User(userName, IDCount, 2, 1, true, "blue"));
                    System.out.println("(" + users.size() + "/20) users have joined.");
                    System.out.println("Welcome to the Grid, " + userName);
                }
                if (users.size() == 3){
                    Game time = new Game();
                    time.start();
                }
                if(users.size() > 2 && users.size() < 20){
                    if(timeUp){
                        gameReady = true;
                        System.out.println("Start Game Now.");
                    }
                else if(users.size() > 20){
                        gameReady = true;
                        System.out.println("Limit reached, start game now.");
                    }
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}


