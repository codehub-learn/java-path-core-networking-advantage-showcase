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
    private boolean shutdown;

    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer(8080);
        tcpServer.startServer();
    }

    public TCPServer(int port) {
        this.port = port;
        shutdown = false;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while(!shutdown) {
                logger.info("Server with ip {} waiting for new connections at port {}",
                        Util.getCorrectIp(),
                        serverSocket.getLocalPort());
                manageClientConnection(serverSocket);
            }
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
            if ("exit".equals(clientMessage)){
                clientOutput.println("Good bye!");
                logger.info("Closing connection with {}",clientConnectionSocket.getInetAddress());
                break;
            }
            else if ("shutdown".equals(clientMessage)){
                clientOutput.println("Server shutting down. Good bye!");
                logger.info("Server shutdown from {}",clientConnectionSocket.getInetAddress());
                shutdown = true;
                break;
            }
            String serverResponse = clientMessage.toUpperCase();
            clientOutput.println(serverResponse);
        }
        } catch (IOException e) {
            logger.error("Client connection exception", e);
        }
    }
}
