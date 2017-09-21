public class User{
    private String name;
    private int currentSpeed;
    private int currentDirection;
    private boolean isJetWallOn;
    User(String name, int speed, int direction, boolean jetWall){
        this.name = name;
        this.currentSpeed = speed;
        this.currentDirection = direction;
        this.isJetWallOn = jetWall;
    }
}
