import java.io.*;
import java.util.*;

public class GridGame {

    static int playerCount = UserJoin.users.size();
    public static String workingDir = (System.getProperty("user.dir") + "\\Game\\");
    private String clientMessage;

    private boolean isGameOver = false;

    public static int[][] theGrid = new int[101][101];

    public static int[][] checkGrid = new int[101][101];

    public static String[] playerPositions = {"100,35", "100,65", "0,50", "0,20", "0,80", "50,0", "50,100", "80,0",
            "80,100", "20,0", "20,100", "100,20", "100,80", "0,35", "0,65", "35,100", "35,0", "65,0", "65,100", "100,50"};

    public static int[] PositionToCoord(String position) {
        String stringCoords[] = position.split(",");
        return Arrays.stream(stringCoords).mapToInt(Integer::parseInt).toArray();
    }


    int gameWinnerID;
    private int secondsPassed = 0;

    public String gridMessage() {
        int playerLeftCount = 0;
        String messageString = "GRID UPDATE ";
        for (int row = 0; row < theGrid.length; row++) {
            for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                if (theGrid[row][columnInt] != 0 && theGrid[row][columnInt] != checkGrid[row][columnInt]) {//if the value is not found in checkGrid (previous loop grid)
                    if (theGrid[row][columnInt] < 21) {
                        playerLeftCount++;
                    }
                    String tempString = theGrid[row][columnInt] + "-" + row + "-" + columnInt + "/";
                    messageString = String.format("%s%s", messageString, tempString);
                }
            }
        }
        if (playerLeftCount >= 2) {
            return messageString;
        } else {
            String gameWinnerName = "";
            isGameOver = true;
            timer.cancel();
            for (int row = 0; row < theGrid.length; row++) {
                for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                    if (theGrid[row][columnInt] > 0 && theGrid[row][columnInt] < 21) {
                        gameWinnerID = theGrid[row][columnInt];
                    }
                }
            }
            try {
                for (User user : UserJoin.users) {
                    if (user.userID == gameWinnerID - 1) {
                        gameWinnerName = user.name;
                    }
                }
                ServerUDP.sendPackets("GAMEWIN " + gameWinnerName);
            } catch (Exception e) {
                e.printStackTrace();
            }


            return "GAME OVER";
        }
    }


    private Timer timer = new Timer();

    private TimerTask clientInput = new TimerTask() {
        @Override
        public void run() {
            int tempUserID = 0;
            int tempDirection;

            clientMessage = ServerUDP.recieveClientPackets();
            if (clientMessage.contains("DIRECTION")) {
                System.out.println(clientMessage);
                if (clientMessage.length() == 18) {
                    tempUserID = Character.getNumericValue(clientMessage.charAt(5)) - 1;
                    tempDirection = Character.getNumericValue(clientMessage.charAt(17));
                } else {
                    tempUserID = Character.getNumericValue(clientMessage.charAt(5)) + Character.getNumericValue(clientMessage.charAt(6)) - 1;
                    tempDirection = Character.getNumericValue(clientMessage.charAt(18));
                }
                for (User user : UserJoin.users) {
                    System.out.println("User ID:" + user.userID + ", TempUSERID: " + tempUserID);
                    if (user.userID == tempUserID) {
                        if (tempDirection == 0) {
                            if (user.currentDirection == 2) {
                                tempDirection = 0;
                            }
                        } else if (tempDirection == 2) {
                            if (user.currentDirection == 0) {
                                tempDirection = 2;
                            }
                        } else if (tempDirection == 1) {
                            if (user.currentDirection == 3) {
                                tempDirection = 1;
                            }
                        } else if (tempDirection == 3) {
                            if (user.currentDirection == 1) {
                                tempDirection = 3;
                            }
                        }
                        user.currentDirection = tempDirection;
                    }
                }
            } else if (clientMessage.contains("LIGHTWALLTOGGLE")) {
                if (clientMessage.length() == 18) {
                    tempUserID = Character.getNumericValue(clientMessage.charAt(5)) - 1;
                } else {
                    tempUserID = Character.getNumericValue(clientMessage.charAt(5)) + Character.getNumericValue(clientMessage.charAt(6)) - 1;
                }
                for (User user : UserJoin.users) {
                    if (tempUserID == user.userID) {
                        if (user.isJetWallOn) {
                            System.out.println("Jetwall off");
                            user.isJetWallOn = false;
                        } else {
                            System.out.println("Jetwall on");
                            user.isJetWallOn = true;
                        }
                    }
                }
            } else if (clientMessage.contains("SPEEDUPDATE")) {
                if (clientMessage.length() == 18) {
                    tempUserID = Character.getNumericValue(clientMessage.charAt(5));
                } else {
                    tempUserID = Character.getNumericValue(clientMessage.charAt(5)) + Character.getNumericValue(clientMessage.charAt(6));
                }
                if (clientMessage.contains("SLOW")) {
                    for (User user : UserJoin.users) {
                        if (tempUserID == user.userID) {
                            System.out.println("slowing user: tempuserID: " + tempUserID + ", user.userID: " + user.userID);
                            user.currentSpeed = 1;
                        }
                    }
                } else if (clientMessage.contains("SPEEDUP")) {
                    for (User user : UserJoin.users) {
                        if (tempUserID == user.userID){
                            System.out.println("speedingup user: tempuserID: " + tempUserID + ", user.userID: " + user.userID);
                            user.currentSpeed = 2;
                        }
                    }
                }
            }
        }
    };

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
                                    if (theGrid[row][columnInt - UserJoin.users.get(tempUserIndex).currentSpeed] == 0 &&
                                            theGrid[row][columnInt - 1] == 0) {
                                        theGrid[row][columnInt - 1] = theGrid[row][columnInt];
                                        theGrid[row][columnInt - UserJoin.users.get(tempUserIndex).currentSpeed] = theGrid[row][columnInt];
                                        if (UserJoin.users.get(tempUserIndex).isJetWallOn) {
                                            theGrid[row][columnInt] += 30;
                                            if (UserJoin.users.get(tempUserIndex).currentSpeed == 2) {
                                                theGrid[row][columnInt - 1] = theGrid[row][columnInt];
                                            }
                                        }
                                    } else {
                                        theGrid[row][columnInt] = 0;
                                    }
                                } else if (UserJoin.users.get(tempUserIndex).currentDirection == 1) {
                                    if (theGrid[row - UserJoin.users.get(tempUserIndex).currentSpeed][columnInt] == 0 &&
                                            theGrid[row - 1][columnInt] == 0) {
                                        theGrid[row - 1][columnInt] = theGrid[row][columnInt];
                                        theGrid[row - UserJoin.users.get(tempUserIndex).currentSpeed][columnInt] = theGrid[row][columnInt];
                                        if (UserJoin.users.get(tempUserIndex).isJetWallOn) {
                                            theGrid[row][columnInt] += 30;
                                            if (UserJoin.users.get(tempUserIndex).currentSpeed == 2) {
                                                theGrid[row - 1][columnInt] = theGrid[row][columnInt];
                                            }
                                        }
                                    } else {
                                        theGrid[row][columnInt] = 0;
                                    }
                                } else if (UserJoin.users.get(tempUserIndex).currentDirection == 2) {
                                    if (theGrid[row][columnInt + UserJoin.users.get(tempUserIndex).currentSpeed] == 0 &&
                                            theGrid[row][columnInt + 1] == 0) {
                                        theGrid[row][columnInt + 1] = theGrid[row][columnInt];
                                        theGrid[row][columnInt + UserJoin.users.get(tempUserIndex).currentSpeed] = theGrid[row][columnInt];
                                        if (UserJoin.users.get(tempUserIndex).isJetWallOn) {
                                            theGrid[row][columnInt] += 30;
                                            if (UserJoin.users.get(tempUserIndex).currentSpeed == 2) {
                                                theGrid[row][columnInt + 1] = theGrid[row][columnInt];
                                            }
                                        }
                                    } else {
                                        theGrid[row][columnInt] = 0;
                                    }
                                } else if (UserJoin.users.get(tempUserIndex).currentDirection == 3) {
                                    if (theGrid[row + UserJoin.users.get(tempUserIndex).currentSpeed][columnInt] == 0 &&
                                            theGrid[row + 1][columnInt] == 0) {
                                        theGrid[row + 1][columnInt] = theGrid[row][columnInt];
                                        theGrid[row + UserJoin.users.get(tempUserIndex).currentSpeed][columnInt] = theGrid[row][columnInt];
                                        if (UserJoin.users.get(tempUserIndex).isJetWallOn) {
                                            theGrid[row][columnInt] += 30;
                                            if (UserJoin.users.get(tempUserIndex).currentSpeed == 2) {
                                                theGrid[row][columnInt + 1] = theGrid[row][columnInt];
                                            }
                                        }
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
                ServerUDP.sendPackets(gridMessage());
                if (gridMessage().contains("GAME OVER")) {
                    System.exit(0);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    private static int resetDirection(int row, int columnInt) {
        if (columnInt == 100) {
            return 0;
        } else if (row == 100) {
            return 1;
        } else if (columnInt == 0) {
            return 2;
        } else if (row == 0) {
            return 3;
        } else {
            return 0;
        }
    }

    public static void positionUsers() {
        Collections.shuffle(UserJoin.users);
        int IDCount = 0;
        for (User user : UserJoin.users) {
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
            for (int columnInt = 0; columnInt < theGrid[row].length; columnInt++) {
                if (theGrid[row][columnInt] == 100) {
                    theGrid[row][columnInt] = UserJoin.users.get(userAssignCount).userID + 1;
                    UserJoin.users.get(userAssignCount).currentDirection = resetDirection(row, columnInt);
                    userAssignCount++;
                }
            }
        }
    }

    public void writeHighScore(int highScore){

        try {
            FileWriter fileWriter =
                    new FileWriter("highscoresheet.txt");

            BufferedWriter bufferedWriter =
                    new BufferedWriter(fileWriter);

            bufferedWriter.write("" + highScore);
            bufferedWriter.close();
        }
        catch(IOException ex) {
            ex.printStackTrace();
        }

    }

    public int findHighScore() {
        String line = null;
        int currentHighScore = 0;
        try {
            FileReader fileReader =
                    new FileReader("highscoresheet.txt");

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                if (Integer.parseInt(line) > currentHighScore){
                    currentHighScore = Integer.parseInt(line);
                }
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            workingDir + "'");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return currentHighScore;
    }



    public void runGame(){
        positionUsers();
        timer.scheduleAtFixedRate(tsk, 20, 50);
        timer.scheduleAtFixedRate(clientInput, 0, 5);
    }

}


