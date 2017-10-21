import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ServerUDP {

    public static String recieveClientPackets(){
        try {
            InetAddress address = InetAddress.getByName("228.5.6.7");
            MulticastSocket socket = new MulticastSocket(49152);

            socket.joinGroup(address);

            byte[] messageBuffer = new byte[1024];
            DatagramPacket recievePacket = new DatagramPacket(messageBuffer, 1024);
            socket.receive(recievePacket);

            return new String(messageBuffer).trim();
        }
        catch (Exception e){return "";}

    }

    public static void sendPackets(String serverMessage) throws Exception{
        InetAddress address = InetAddress.getByName("228.5.6.7");
        MulticastSocket socket = new MulticastSocket(49152);

        socket.joinGroup(address);

        DatagramPacket packet = new DatagramPacket(serverMessage.getBytes(), serverMessage.length(), address, 49152);
        socket.send(packet);
        socket.leaveGroup(address);
        socket.close();
    }
}
