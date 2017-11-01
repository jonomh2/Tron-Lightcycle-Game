import java.util.*;

public class GridGame {
    static int playerCount = UserJoin.users.size();

    private boolean isGameOver = false;

    public static int[][] theGrid = new int[101][101];

    public static int[][] checkGrid = new int[101][101];

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
                if (theGrid[row][columnInt] != 0 && theGrid[row][columnInt] != checkGrid[row][columnInt]){ //if the value is not found in checkGrid (previous loop grid)
                    String tempString = theGrid[row][columnInt] + "-" + row + "-" + columnInt + "/";
                    messageString = String.format("%s%s", messageString, tempString);
                }
            }
        }
        if (messageString.length() > 12){
            return messageString;
        }
        else {
            isGameOver = true;
            timer.cancel();
            return "GAME OVER";
        }
    }

    private Timer timer = new Timer();

    private TimerTask tsk = new TimerTask() {
        @Override
        public void run() {
                //add old values to checkGrid for checking
                for (int row = 0; row < theGrid.length; row++) {
                    for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                        checkGrid[row][columnInt] = theGrid[row][columnInt];
                    }
                }
                int repeatLoop = 0;
                ArrayList<Integer> noRepeat = new ArrayList<Integer>();
                noRepeat.clear();
                secondsPassed++;
                int tempUserIndex = 0;
                for (int row = 0; row < theGrid.length; row++) { //runs through each number
                    for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                        if (theGrid[row][columnInt] < 21 && theGrid[row][columnInt] > 0) { //if it's a player number
                            int userCount = 0;
                            boolean numRepeated = false;
                            for (int num : noRepeat) {
                                if (theGrid[row][columnInt] == num) {
                                    numRepeated = true;
                                    break;
                                }
                            }
                            for (User user : UserJoin.users) {//run through each user
                                if ((theGrid[row][columnInt] - 1) == user.userID) {//if it's a particular user's number
                                    tempUserIndex = userCount;//assign that user's index to a variable//add checker for next lines
                                } else {
                                    userCount++;
                                }
                            }
                            if (!numRepeated) {
                                try {
                                    noRepeat.add(tempUserIndex + 1);
                                    if (UserJoin.users.get(tempUserIndex).currentDirection == 0) {
                                        if (theGrid[row][columnInt - 1] == 0) {
                                            theGrid[row][columnInt - 1] = theGrid[row][columnInt];
                                            theGrid[row][columnInt] += 30;
                                        } else {
                                            theGrid[row][columnInt] = 0;
                                        }
                                    } else if (UserJoin.users.get(tempUserIndex).currentDirection == 1) {
                                        if (theGrid[row - 1][columnInt] == 0) {
                                            theGrid[row - 1][columnInt] = theGrid[row][columnInt];
                                            theGrid[row][columnInt] += 30;
                                        } else {
                                            theGrid[row][columnInt] = 0;
                                        }
                                    } else if (UserJoin.users.get(tempUserIndex).currentDirection == 2) {
                                        if (theGrid[row][columnInt + 1] == 0) {
                                            theGrid[row][columnInt + 1] = theGrid[row][columnInt];
                                            theGrid[row][columnInt] += 30;
                                        } else {
                                            theGrid[row][columnInt] = 0;
                                        }
                                    } else if (UserJoin.users.get(tempUserIndex).currentDirection == 3) {
                                        if (theGrid[row + 1][columnInt] == 0) {
                                            theGrid[row + 1][columnInt] = theGrid[row][columnInt];
                                            theGrid[row][columnInt] += 30;
                                        } else {
                                            theGrid[row][columnInt] = 0;
                                        }
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    theGrid[row][columnInt] = 0;
                                }
                            }
                        }
                    }
                }
                try {
                    System.out.println(gridMessage());
                    ServerUDP.sendPackets(gridMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    };


    private static int resetDirection(int row, int columnInt){
        if (columnInt == 100){
            return 0;
        }

        else if (row == 100){
            return 1;
        }

        else if (columnInt == 0){
            return 2;
        }

        else if (row == 0){
            return 3;
        }

        else{
            return 0;
        }
    }

    public static void positionUsers() {
//        UserJoin.users.add(new User("User 1", 1, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 2", 2, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 3", 3, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 4", 4, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 5", 5, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 6", 6, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 7", 7, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 8", 8, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 9", 9, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 10", 10, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 11", 11, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 12", 12, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 13", 13, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 14", 14, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 15", 15, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 16", 16, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 17", 17, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 18", 18, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 19", 19, 2,  0, true, "blue"));
//        UserJoin.users.add(new User("User 20", 20, 2,  0, true, "blue"));
        Collections.shuffle(UserJoin.users);
        int IDCount = 0;
        for (User user : UserJoin.users){
            user.userID = IDCount;
            IDCount++;
        }
        for (int i = 0; i < UserJoin.users.size(); i++) {
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
                    theGrid[row][columnInt] = UserJoin.users.get(userAssignCount).userID + 1;
                    UserJoin.users.get(userAssignCount).currentDirection = resetDirection(row, columnInt);
                    userAssignCount++;
                }
            }
        }
    }

    public void runGame(){
        UserJoin.init();
        positionUsers();
        timer.scheduleAtFixedRate(tsk, 0, 200);
    }

}


