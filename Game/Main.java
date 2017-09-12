public class Main {
    public static void main(String[] args) throws Exception{
    String compressedGrid = "Server message";
        ServerUDP.sendPackets(compressedGrid);
        Game.init();
    }
}