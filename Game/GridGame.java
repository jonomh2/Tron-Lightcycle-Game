import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GridGame {
    static int playerCount = Game.users.size();

    public static int[][] theGrid = new int[200][200];

    public static void positionUsers(){
        Game.users.add(new User("jef", 2, 1, true));
        System.out.println(playerCount);
        for (int i = 0;i < playerCount;i++){
            System.out.println("player" + i);
    }
        }
    }


//            for (int j = 0; j < theGrid[0].length; j++){
//        for (int i = 0; i<theGrid.length; i++){
//        System.out.println(j + "," + i);
