import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.*;
import java.util.stream.IntStream;

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
        String messageString = "GRID UPDATE ";
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
            int repeatLoop = 0;
            ArrayList<Integer> noRepeat = new ArrayList<Integer>();
            noRepeat.clear();
            secondsPassed++;
            int tempUserIndex = 0;
            for (int row = 0; row < theGrid.length; row++) { //runs through each number
                for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                    if (theGrid[row][columnInt] < 21 && theGrid[row][columnInt] > 0){//if it's a player number
                        int userCount = 0;
                        boolean numRepeated = false;
                        for (int num : noRepeat){
                            if (theGrid[row][columnInt] == num){
                                numRepeated = true;
                                break;
                            }
                        }
                        for (User user : Game.users) {//run through each user
                            if ((theGrid[row][columnInt] - 1) == user.userID) {//if it's a particular user's number
                                tempUserIndex = userCount;//assign that user's index to a variable//add checker for next lines
                            }
                            else {
                                userCount++;
                            }
                        }
                        if (!numRepeated) {
                            noRepeat.add(tempUserIndex + 1);
                            if (Game.users.get(tempUserIndex).currentDirection == 0) {
                                System.out.println(Game.users.get(tempUserIndex).userID + " is going left");
                                theGrid[row][columnInt - 1] = theGrid[row][columnInt];
                                theGrid[row][columnInt] += 30;
                            }

                            else if (Game.users.get(tempUserIndex).currentDirection == 1) {
                                System.out.println(Game.users.get(tempUserIndex).userID + " is going up");
                                theGrid[row - 1][columnInt] = theGrid[row][columnInt];
                                theGrid[row][columnInt] += 30;
                            }

                            else if (Game.users.get(tempUserIndex).currentDirection == 2) {
                                System.out.println(Game.users.get(tempUserIndex).userID + " is going right");
                                theGrid[row][columnInt + 1] = theGrid[row][columnInt];
                                theGrid[row][columnInt] += 30;
                            }

                            else if (Game.users.get(tempUserIndex).currentDirection == 3) {
                                System.out.println(Game.users.get(tempUserIndex).userID + " is going down");
                                theGrid[row + 1][columnInt] = theGrid[row][columnInt];
                                theGrid[row][columnInt] += 30;
                            }
                            else{
                                System.out.println("something failed to move");
                            }
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
        System.out.print((userID - 1) + " should == ");
        if (userID == 7 || userID == 9 || userID == 11 || userID == 13 || userID == 15){
            System.out.println("0/left");
            return 0;
        }

        else if (userID == 16 || userID == 17 || userID == 18 || userID == 19 || userID == 20){
            System.out.println("1/up");
            return 1;
        }

        else if (userID == 6 || userID == 8 || userID == 10 || userID == 12 || userID == 14){
            System.out.println("2/right");
            return 2;
        }

        else if (userID == 1 || userID == 2 || userID == 3 || userID == 4 || userID == 5){
            System.out.println("3/down");
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
            for (int row = 0; row < theGrid.length; row++) {
                for (int column = 0; column < theGrid[row].length; column++) {
                    if (row == tempY) {
                        if (column == tempX) {
                            theGrid[row][column] = 100;
                        }
                    }
                }
            }
        }


        int userAssignCount = 0;
        for (int row = 0; row < theGrid.length; row++) {
            for (int columnInt = 0; columnInt < theGrid[row].length ; columnInt++) {
                if (theGrid[row][columnInt] == 100) {
                    theGrid[row][columnInt] = Game.users.get(userAssignCount).userID + 1;
                    Game.users.get(userAssignCount).currentDirection = resetDirection(Game.users.get(userAssignCount).userID + 1);
                    userAssignCount++;
                }
            }
        }

//        for (int row = 0; row < theGrid.length; row++) {
//            System.out.println();
//            for (int columnInt = 0; columnInt < theGrid[row].length ; columnInt++) {
//                System.out.print(theGrid[row][columnInt] + " ");
//            }
//            }
    }

    public void runGame(){
        positionUsers();
        timer.scheduleAtFixedRate(tsk, 0, 200);
    }

}


