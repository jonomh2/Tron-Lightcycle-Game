import java.util.Objects;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws Exception{
        final String INSTRUCTIONS = "instructions";

        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Tron game\nPlease select an option:");
        System.out.println("(P)lay Game\n(I)nstructions\n(Q)uit\n>");
        String userChoice = input.next();

        while (!Objects.equals(userChoice.toUpperCase(), "Q")){
            if(Objects.equals(userChoice.toUpperCase(), "P")){
                System.out.println("Please enter your name:");
                String userName = input.next();
                ClientUDP.sendPackets("ADD USER " + userName);
                System.out.println("(P)lay Game\n(I)nstructions\n(Q)uit\n>");
                userChoice = input.next();
            }
            else if(Objects.equals(userChoice.toUpperCase(), "I")){
                System.out.println(INSTRUCTIONS);
            }
            else{
                System.out.println("Try again.");
                System.out.println("(P)lay Game\n(I)nstructions\n(Q)uit\n>");
                userChoice = input.next();
            }
        }
    }
}
