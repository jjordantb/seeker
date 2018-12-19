package io.paratek.seeker.server.connect;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ConnectionManager {

    private static Logger LOG = Logger.getLogger(ConnectionManager.class.getName());

    private final List<ServerConnection> serverConnections = new ArrayList<>();

    public void create(final Socket socket) {
        final ServerConnection serverConnection = new ServerConnection();
        serverConnection.accept(socket);
        this.serverConnections.add(serverConnection);
        LOG.info("Created Server Connection " + serverConnection);
    }

    public void close(final ServerConnection serverConnection) {
        if (this.serverConnections.contains(serverConnection)) {
            this.serverConnections.remove(serverConnection);
            serverConnection.close();
        }
    }

    public List<ServerConnection> getConnections() {
        return this.serverConnections;
    }

}
