package io.paratek.seeker.server;

import io.paratek.seeker.server.connect.ConnectionManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class Server {

    private static final Logger LOG = Logger.getLogger(Server.class.getName());

    private boolean running = true;
    private final ServerSocket serverSocket;
    private final ConnectionManager connectionManager = new ConnectionManager();

    public Server(int port) throws IOException {
        this.serverSocket = new ServerSocket(port);
    }

    public void start() {
        LOG.info("Starting Seeker Server");
        while (this.isRunning()) {
            try {
                final Socket socket = this.serverSocket.accept();
                if (socket != null) {
                    this.connectionManager.create(socket);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isRunning() {
        return running;
    }

    public void shutdown() {
        this.running = false;
    }

}
