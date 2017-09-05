import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class Client {
    public static void main(String[] args) throws Exception{
        InetAddress address = InetAddress.getByName("228.5.6.7");
        MulticastSocket socket = new MulticastSocket(49152);

        socket.joinGroup(address);

        byte[] messageBuffer = new byte[1024];
        DatagramPacket recievePacket = new DatagramPacket(messageBuffer, 1024);
        socket.receive(recievePacket);

        String resultStr = new String(messageBuffer);
        System.out.println("Message recieved: " + resultStr);

    }
}
