package gr.codelearn.showcase.networking;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        basicNetInfo();
    }

    public static void basicNetInfo(){
        InetAddress local = null;

        try {
            local = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        logger.info("Address is {}",local);
        logger.info("Host Address is {}",local.getHostAddress());
        logger.info("Canonical address is {}",local.getCanonicalHostName());
        logger.info("Hostname is {}",local.getHostName());
    }
}
