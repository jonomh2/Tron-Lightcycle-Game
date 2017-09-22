import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridGame {
    static int playerCount = Game.users.size();

    public static String[] playerPositions = {"200,75", "200,125", "0,100", "0,50", "0,150", "100,0", "100,200", "150,0",
    "150,200", "50,0", "50,200", "200,50", "200,150", "0,75","0,125","75,200", "75,0","125,0","125,200", "200,100"};

    public static int[][] theGrid = new int[200][200];

    public static void positionUsers(){
        Game.users.add(new User("jef", 2, 1, true));
        System.out.println(playerCount);
        for (int i = 0;i < playerCount;i++){

    }
        }
    }


//            for (int j = 0; j < theGrid[0].length; j++){
//        for (int i = 0; i<theGrid.length; i++){
//        System.out.println(j + "," + i);
