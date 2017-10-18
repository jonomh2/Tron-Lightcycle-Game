public class User{
    public String name;
    public int userID;
    public int currentSpeed;
    public int currentDirection;
    public boolean isJetWallOn;
    public String userColour;
    User(String name, int ID, int speed, int direction, boolean jetWall, String colour){
        this.name = name;
        this.userID = ID;
        this.currentSpeed = speed;
        this.currentDirection = direction;
        this.isJetWallOn = jetWall;
        this.userColour = colour;
    }
}
