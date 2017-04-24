package edu.cse4232.gossip.server;

import edu.cse4232.gossip.helper.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class TCPServer implements Runnable, AutoCloseable {

    private ServerSocket tcpServer;
    private Logger log;

    public TCPServer(int port) throws IOException {
        this.tcpServer = new ServerSocket(port);
        this.log = Logger.getInstance();
    }

    /**
     * Closes TCP Socket
     * @throws Exception Socket.close()
     */
    @Override
    public void close() throws Exception {
        if (tcpServer != null) tcpServer.close();
    }

    /**
     * Accepts TCP Client Requests
     * Generates new Thread to Handle Client
     */
    @Override
    public void run() {

        for (; ; ) {

            try {

                Socket client = tcpServer.accept();
                new Thread(new TCPResponder(client)).start();
                log.log(Logger.TCP, Logger.SERVER, Logger.WARN, String.format("Connected to %s", client.getRemoteSocketAddress()));

            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}