import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.*;

public class GridGame {
    static int playerCount = Game.users.size();

    public static int[][] theGrid = new int[101][101];

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
                if (theGrid[row][columnInt] != 0 && theGrid[row][columnInt] != 100){
                    String tempString = theGrid[row][columnInt] + "-" + row + "-" + columnInt + "/";
                    messageString = String.format("%s%s", messageString, tempString);
                }
            }
        }
        System.out.println(messageString);
        return messageString;
    }

    private Timer timer = new Timer();

    private TimerTask tsk = new TimerTask() {
        @Override
        public void run() {
            secondsPassed++;
            int tempUserIndex = 0;
            for (int row = 0; row < theGrid.length; row++) {
                for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                    if (theGrid[row][columnInt] < 21 && theGrid[row][columnInt] > 0){
                        int userCount = 0;
                        for(User user: Game.users){
                            if((theGrid[row][columnInt]) == user.userID){
                                tempUserIndex = userCount;
                            }
                            userCount++;
                        }

                        if(Game.users.get(tempUserIndex).currentDirection == 0){
                            theGrid[row][columnInt - 1] = theGrid[row][columnInt];
                            theGrid[row][columnInt] = (theGrid[row][columnInt] + 30);
                        }

                        else if(Game.users.get(tempUserIndex).currentDirection == 1){
                            theGrid[row - 1][columnInt] = theGrid[row][columnInt];
                            theGrid[row][columnInt] = (theGrid[row][columnInt] + 30);
                        }

                        else if(Game.users.get(tempUserIndex).currentDirection == 2){
                            theGrid[row][columnInt] = (theGrid[row][columnInt] + 30);
                        }

                        else if(Game.users.get(tempUserIndex).currentDirection == 0){
                            theGrid[row - 1][columnInt] = theGrid[row][columnInt];
                            theGrid[row][columnInt] = (theGrid[row][columnInt] + 30);
                        }
                    }
                }
            }
            try {
                ServerUDP.sendPackets(gridMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };


    private static int resetDirection(int userID){
        if (userID == 7 || userID == 9 || userID == 11 || userID == 13 || userID == 15){
            System.out.println("User " + Game.users.get(userID - 1).userID + "'s direction is 0/left");
            return 0;
        }

        else if (userID == 16 || userID == 17 || userID == 18 || userID == 19 || userID == 20){
            System.out.println("User " + Game.users.get(userID - 1).userID + "'s direction is 1/up");
            return 1;
        }

        else if (userID == 6 || userID == 8 || userID == 10 || userID == 12 || userID == 14){
            System.out.println("User " + Game.users.get(userID - 1).userID + "'s direction is 2/right");
            return 2;
        }

        else if (userID == 1 || userID == 2 || userID == 3 || userID == 4 || userID == 5){
            System.out.println("User " + Game.users.get(userID - 1).userID + "'s direction is 3/down");
            return 3;
        }

        else{
            return 0;
        }
    }

    public static void positionUsers() {
        Game.users.add(new User("User 1", 1, 2,  0, true, "blue"));
        Game.users.add(new User("User 2", 2, 2,  0, true, "blue"));
        Game.users.add(new User("User 3", 3, 2,  0, true, "blue"));
        Game.users.add(new User("User 4", 4, 2,  0, true, "blue"));
        Game.users.add(new User("User 5", 5, 2,  0, true, "blue"));
        Game.users.add(new User("User 6", 6, 2,  0, true, "blue"));
        Game.users.add(new User("User 7", 7, 2,  0, true, "blue"));
        Game.users.add(new User("User 8", 8, 2,  0, true, "blue"));
        Game.users.add(new User("User 9", 9, 2,  0, true, "blue"));
        Game.users.add(new User("User 10", 10, 2,  0, true, "blue"));
        Game.users.add(new User("User 11", 11, 2,  0, true, "blue"));
        Game.users.add(new User("User 12", 12, 2,  0, true, "blue"));
        Game.users.add(new User("User 13", 13, 2,  0, true, "blue"));
        Game.users.add(new User("User 14", 14, 2,  0, true, "blue"));
        Game.users.add(new User("User 15", 15, 2,  0, true, "blue"));
        Game.users.add(new User("User 16", 16, 2,  0, true, "blue"));
        Game.users.add(new User("User 17", 17, 2,  0, true, "blue"));
        Game.users.add(new User("User 18", 18, 2,  0, true, "blue"));
        Game.users.add(new User("User 19", 19, 2,  0, true, "blue"));
        Game.users.add(new User("User 20", 20, 2,  0, true, "blue"));
        Collections.shuffle(Game.users);
        int IDCount = 0;
        for (User user : Game.users){
            user.userID = IDCount;
            IDCount++;
        }
        for (int i = 0; i < Game.users.size(); i++) {
            int tempY = PositionToCoord(playerPositions[i])[0];
            int tempX = PositionToCoord(playerPositions[i])[1];
            int tempCounter = 0;
            for (int row = 0; row < theGrid.length; row++) {
                for (int column = 0; column < theGrid[row].length; column++) {
                    if (row == tempY) {
                        if (column == tempX) {
                            tempCounter++;
                            theGrid[row][column] = tempCounter;
                        }
                    }
                }
            }
        }


        int userAssignCount = 0;
        for (int row = 0; row < theGrid.length; row++) {
            for (int columnInt = 0; columnInt < theGrid[row].length ; columnInt++) {
                if (theGrid[row][columnInt] != 0) {
                    User tempUser = Game.users.get(userAssignCount);
                    tempUser.currentDirection = resetDirection(tempUser.userID + 1);
                    userAssignCount++;
                }
            }
        }
    }

    public void runGame(){
        positionUsers();
        timer.scheduleAtFixedRate(tsk, 0, 200);
    }

}


