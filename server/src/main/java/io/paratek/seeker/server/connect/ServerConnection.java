package io.paratek.seeker.server.connect;

import io.paratek.seeker.game.ServerStates;
import io.paratek.seeker.net.Connection;
import io.paratek.seeker.net.Opcodes;
import io.paratek.seeker.net.Packet;
import io.paratek.seeker.scene.entity.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerConnection extends Connection {

    private static final Logger LOG = Logger.getLogger(ServerConnection.class.getName());

    private Player player = null;

    public void accept(final Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void run() {
        while (super.connected) {
            try {
                final Packet read = this.read();
                int op = read.getOp();
                if (op == Opcodes.LOGIN_REQUEST) {
                    if (player != null) {
                        continue;
                    }
                    this.player = (Player) read.getPayload();
                    LOG.info("Player " + this.player.getName() + " logging in.");
                    if (ServerStates.PLAYER_STATES.loginPlayer(this.player, this)) {
                        LOG.info("Player " + this.player + " logged in successfully.");
                    }
                } else if (op == Opcodes.LOGOUT) {
                    ServerStates.PLAYER_STATES.logoutPlayer(this.player);
                } else if (op == Opcodes.UPDATE_PLAYER_LOCATION) {
                    this.player = (Player) read.getPayload();
                    ServerStates.PLAYER_STATES.pushUpdatedPlayer(this.player);
                }
            } catch (IOException e) {
                // Connection Closed
                ServerStates.PLAYER_STATES.logoutPlayer(this.player);
                this.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
