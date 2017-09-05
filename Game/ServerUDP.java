import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServerUDP {
    public static void recievePackets(){
        /*
        possible incoming message example:
        "USERJeremy,SPEED2,DIRECTION2,JETWALLtrue"
         */
    }
    public static void sendPackets(String compressedGrid) throws Exception{
        InetAddress address = InetAddress.getByName("228.5.6.7");
        MulticastSocket socket = new MulticastSocket(49152);

        socket.joinGroup(address);

        DatagramPacket packet = new DatagramPacket(compressedGrid.getBytes(), compressedGrid.length(), address, 49152);
        socket.send(packet);
        socket.leaveGroup(address);
        socket.close();
    }
}
