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

    public static int[][] theGrid = new int[101][101];

    public static void positionUsers() {
        Game.users.add(new User("User1", 2, 2, 1, true));
        Game.users.add(new User("User2", 2, 2, 1, true));
        Game.users.add(new User("User3", 2, 2, 1, true));
        Game.users.add(new User("User4", 2, 2, 1, true));
        Game.users.add(new User("User5", 2, 2, 1, true));
        Game.users.add(new User("User6", 2, 2, 1, true));
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
            IDCount++;
            user.userID = IDCount;
        }

        for (User user : Game.users){
            System.out.println(user.name + " " + user.userID);
        }
        int userAssignCount = 0;
        for (int i = 0; i < Game.users.size(); i++) {
            for (int row = 0; row < theGrid.length; row++) {
                for (int column = 0; column < theGrid[row].length; column++) {
                    if (column == 100) {
                        column = Game.users.get(userAssignCount).userID;
                    }
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
}

