package io.paratek.seeker.client.net;

import io.paratek.seeker.client.Game;
import io.paratek.seeker.net.Connection;
import io.paratek.seeker.net.Opcodes;
import io.paratek.seeker.net.Packet;
import io.paratek.seeker.scene.entity.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientConnection extends Connection {

    private static final Logger LOG = Logger.getLogger(ClientConnection.class.getName());

    public void open(String host, int port) throws IOException {
        this.socket = new Socket(host, port);
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
                final int op = read.getOp();
                if (op == Opcodes.LOGIN_SUCCESSFUL) {
                    LOG.info("Login Successful!");
                    final Player player = (Player) read.getPayload();
                    Game.SCENE_GRAPH.localPlayer = player;
                    Game.SCENE_GRAPH.players.put(player.getName(), player);
                } else if (op == Opcodes.ADD_PLAYER) {
                    final Player player = (Player) read.getPayload();
                    Game.SCENE_GRAPH.players.put(player.getName(), player);
                } else if (op == Opcodes.UPDATE_PLAYER_LOCATION) {
                    final Player player = (Player) read.getPayload();
                    Game.SCENE_GRAPH.players.replace(player.getName(), player);
                    if (player.getName().equals(Game.SCENE_GRAPH.localPlayer.getName())) {
                        Game.SCENE_GRAPH.localPlayer = player;
                    }
                } else if (op == Opcodes.LOGOUT) {
                    final Player player = (Player) read.getPayload();
                    if (player.getName().equals(Game.SCENE_GRAPH.localPlayer.getName())) {
                        LOG.info("Kicked from Server? IDK");
                    } else {
                        Game.SCENE_GRAPH.players.remove(player.getName());
                    }
                }
            } catch (IOException e) {
                LOG.severe("SERVER CRASH");
                this.close();
                System.exit(-1);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
