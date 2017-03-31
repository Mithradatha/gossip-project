package com.cse4232.gossip.server;

import com.cse4232.gossip.helper.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer implements Runnable {

    private int port;
    private Logger logger;

    TCPServer(int port) {
        this.port = port;
        this.logger = Logger.getInstance();
    }

    @Override
    public void run() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    TCPClientHandler newHandler = new TCPClientHandler(clientSocket);
                    new Thread(newHandler).start();
                } catch (IOException ex) {
                    logger.log(ex);
                }
            }
        } catch (IOException e) {
            logger.log(e);
        }
    }
}