public class User{
    public String name;
    public int userID;
    public int currentSpeed;
    public int currentDirection;
    public boolean isJetWallOn;
    User(String name, int ID, int speed, int direction, boolean jetWall){
        this.name = name;
        this.userID = ID;
        this.currentSpeed = speed;
        this.currentDirection = direction;
        this.isJetWallOn = jetWall;
    }
}
