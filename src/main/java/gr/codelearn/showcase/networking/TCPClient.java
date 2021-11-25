package gr.codelearn.showcase.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
    private static final Logger logger = LoggerFactory.getLogger(TCPClient.class);
    private final int port;

    public static void main(String[] args) {
        TCPClient tcpClient = new TCPClient(8080);
        tcpClient.startClient();
    }

    public TCPClient(int port) {
        this.port = port;
    }

    public void startClient(){
        try(
                Socket clientSocket = new Socket(Util.getCorrectIp(),port);
                BufferedReader clientInput = new BufferedReader(new InputStreamReader(System.in));
                BufferedReader serverInput = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintStream serverOutput = new PrintStream(clientSocket.getOutputStream())
                ) {
            logger.info("Connected to {} on port {}", clientSocket.getInetAddress(),port);

            while (true){
                logger.info("Type a message to send at server: ");
                // read client message
                String message = clientInput.readLine();
                // send message to server
                serverOutput.println(message);
                // get server message
                String serverResponseMessage = serverInput.readLine();

                logger.info("Server response message: {}", serverResponseMessage);

                if ("exit".equals(message) || "shutdown".equals(message))
                    break;
            }

            logger.info("Closing socket!");
        } catch (UnknownHostException e) {
            logger.info("Unknown host exception",e);
        } catch (IOException e) {
            logger.info("Unknown input output exception",e);
        }
    }
}