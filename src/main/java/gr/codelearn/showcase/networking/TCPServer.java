package gr.codelearn.showcase.networking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final Logger logger = LoggerFactory.getLogger(TCPServer.class);
    private final int port;

    public TCPServer(int port) {
        this.port = port;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server with ip {} waiting for new connections at port {}",
                    serverSocket.getInetAddress().getHostAddress(),
                    serverSocket.getLocalPort());
            manageClientConnection(serverSocket);
        } catch (IOException e) {
            logger.error("Unknown input output exception", e);
        }
    }

    private void manageClientConnection(ServerSocket serverSocket) {
        try (
                Socket clientConnectionSocket = serverSocket.accept();
                BufferedReader clientInput = new BufferedReader(new InputStreamReader(clientConnectionSocket.getInputStream()));
                PrintStream clientOutput = new PrintStream(clientConnectionSocket.getOutputStream())
        ) {
        logger.info("Client connection accepted with ip: {}",clientConnectionSocket.getInetAddress());

        while(true){
            String clientMessage = clientInput.readLine();
            logger.info("Message received: {}",clientMessage);
            String serverResponse = clientMessage.toUpperCase();
            clientOutput.println(serverResponse);
        }
        } catch (IOException e) {
            logger.error("Client connection exception", e);
        }
    }
}
