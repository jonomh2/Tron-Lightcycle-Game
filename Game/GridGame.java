import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.*;

public class GridGame {
    static int playerCount = Game.users.size();

    public static String[] playerPositions = {"100,35", "100,65", "0,50", "0,20", "0,80", "50,0", "50,100", "80,0",
            "80,100", "20,0", "20,100", "100,20", "100,80", "0,35", "0,65", "35,100", "35,0", "65,0", "65,100", "100,50"};

    public static int[] PositionToCoord(String position) {
        String stringCoords[] = position.split(",");
        return Arrays.stream(stringCoords).mapToInt(Integer::parseInt).toArray();
    }

    private int secondsPassed = 0;

    public String gridMessage(){
        String messageString = "";
        for (int row = 0; row < theGrid.length; row++) {
            for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                if (theGrid[row][columnInt] != 0){
                    String tempString = theGrid[row][columnInt] + "-" + row + "," + columnInt + "/";
                    messageString = String.format("%s%s", messageString, tempString);
                }
            }
        }
        return messageString;
    }

    private Timer timer = new Timer();

    private TimerTask tsk = new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
            for (int row = 0; row < theGrid.length; row++) {
                for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                    if (theGrid[row][columnInt] != 0){
                        if (Game.users.get(theGrid[row][columnInt]).currentDirection == 0){
                            theGrid[row - 1][columnInt] = Game.users.get(theGrid[row][columnInt]).userID;
                            if (Game.users.get(theGrid[row][columnInt]).isJetWallOn) {
                                theGrid[row][columnInt] = 100;
                            }
                            else{
                                theGrid[row][columnInt] = 0;
                            }
                        }
                        else if (Game.users.get(theGrid[row][columnInt]).currentDirection == 1){
                            theGrid[row][columnInt - 1] = Game.users.get(theGrid[row][columnInt]).userID;
                            if (Game.users.get(theGrid[row][columnInt]).isJetWallOn) {
                                theGrid[row][columnInt] = 100;
                            }
                            else{
                                theGrid[row][columnInt] = 0;
                            }
                        }
                        else if (Game.users.get(theGrid[row][columnInt]).currentDirection == 2){
                            theGrid[row + 1][columnInt] = Game.users.get(theGrid[row][columnInt]).userID;
                            if (Game.users.get(theGrid[row][columnInt]).isJetWallOn) {
                                theGrid[row][columnInt] = 100;
                            }
                            else{
                                theGrid[row][columnInt] = 0;
                            }
                        }
                        else if (Game.users.get(theGrid[row][columnInt]).currentDirection == 2){
                            theGrid[row][columnInt + 1] = Game.users.get(theGrid[row][columnInt]).userID;
                            if (Game.users.get(theGrid[row][columnInt]).isJetWallOn) {
                                theGrid[row][columnInt] = 100;
                            }
                            else{
                                theGrid[row][columnInt] = 0;
                            }
                        }
                        try {
                            ServerUDP.sendPackets("GRID UPDATE" + gridMessage());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        }

    };

    public static int[][] theGrid = new int[101][101];

    private static int resetDirection(int userID){

        if (userID == 7 || userID == 9 || userID == 11 || userID == 13 || userID == 15){
            return 0;
        }

        else if (userID == 1 || userID == 2 || userID == 3 || userID == 4 || userID == 5){
            return 3;
        }

        else if (userID == 6 || userID == 8 || userID == 10 || userID == 12 || userID == 14){
            return 2;
        }

        else if (userID == 16 || userID == 17 || userID == 18 || userID == 19 || userID == 20){
            return 3;
        }

        else{
            return 1;
        }
    }

    public static void positionUsers() {
        Game.users.add(new User("User 1", 0, 2,  0, true, "blue"));
        Game.users.add(new User("User 2", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 3", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 4", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 5", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 6", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 7", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 8", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 9", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 10", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 11", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 12", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 13", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 14", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 15", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 16", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 17", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 18", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 19", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 20", 1, 2,  0, true, "blue"));
        Collections.shuffle(Game.users);
        for (int i = 0; i < Game.users.size(); i++) {
            int tempX = PositionToCoord(playerPositions[i])[0];
            int tempY = PositionToCoord(playerPositions[i])[1];
            for (int row = 0; row < theGrid.length; row++) {
                for (int column = 0; column < theGrid[row].length; column++) {
                    if (row == tempX) {
                        if (column == tempY) {
                            theGrid[row][column] = 100;
                        }
                    }
                }
            }
        }
        int IDCount = 0;
        for (User user : Game.users){
            user.userID = IDCount;
            IDCount++;
        }


        int userAssignCount = 0;
        for (int row = 0; row < theGrid.length; row++) {
            for (int columnInt = 0; columnInt < theGrid[row].length ; columnInt++) {
                if (theGrid[row][columnInt] == 100) {
                    theGrid[row][columnInt] = Game.users.get(userAssignCount).userID + 1;
                    System.out.println(Game.users.get(userAssignCount).name + " " + columnInt + "," + row + " The ID is:" + Game.users.get(userAssignCount).userID);
                    Game.users.get(userAssignCount).currentDirection = resetDirection(Game.users.get(userAssignCount).userID);
                    userAssignCount++;
                }
            }
        }


        for (int j = 0; j < theGrid[0].length; j++) {
            System.out.print("\n");
            for (int f = 0; f < theGrid.length; f++) {
                System.out.print(theGrid[j][f] + " ");

            }
        }
    }

    public void runGame(){
        timer.scheduleAtFixedRate(tsk, 0, 35);
        for (int row = 0; row < theGrid.length; row++) {
            for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {

            }
        }
    }

    public static void main(String[] args) {
        GridGame game = new GridGame();
        game.timer.scheduleAtFixedRate(game.tsk, 0, 35);
    }
}


