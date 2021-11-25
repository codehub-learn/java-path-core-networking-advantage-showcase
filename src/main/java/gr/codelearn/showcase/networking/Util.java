package gr.codelearn.showcase.networking;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Util {
    private Util() {
    }

    public static String getCorrectIp(){
        String ipCorrect = null;
        try(DatagramSocket socket = new DatagramSocket()) {
            socket.connect(InetAddress.getByName("www.google.gr"), 80);
            ipCorrect = socket.getLocalAddress().getHostAddress();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
        return ipCorrect;
    }
}
