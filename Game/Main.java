public class Main {
    public static void main(String[] args) throws Exception{
//        GridGame game = new GridGame();
//        game.runGame();
        new ClientUI();
        ClientUI.convertMessage("GRID UPDATE30-20-20/10-60-20/");
        Thread.sleep(20);
        ClientUI.convertMessage("GRID UPDATE30-20-30/10-50-20/");
        Thread.sleep(20);
        ClientUI.convertMessage("GRID UPDATE30-20-40/10-40-20/");
        Thread.sleep(20);
        ClientUI.convertMessage("GRID UPDATE30-20-50/10-30-20/");
        Thread.sleep(20);
        ClientUI.convertMessage("GRID UPDATE30-20-60/10-20-20/");
        Thread.sleep(20);
        ClientUI.convertMessage("GRID UPDATE30-20-70/10-10-20/");
        Thread.sleep(20);
    }
}