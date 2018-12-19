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
import java.nio.ByteBuffer;
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
                    final Player player = new Player(new String(read.getPayload()));
                    Game.SCENE_GRAPH.localPlayer = player;
                    Game.SCENE_GRAPH.players.put(player.getName(), player);
                } else if (op == Opcodes.ADD_PLAYER) {
                    final Player player = new Player(new String(read.getPayload()));
                    Game.SCENE_GRAPH.players.put(player.getName(), player);
                } else if (op == Opcodes.UPDATE_PLAYER_LOCATION) {
                    byte[] nameBytes = new byte[200];
                    System.arraycopy(read.getPayload(), 0, nameBytes, 0, nameBytes.length);
                    final String name = new String(nameBytes);
                    final Player player = Game.SCENE_GRAPH.players.get(name);
                    byte[] x = new byte[16];
                    byte[] y = new byte[16];
                    System.arraycopy(read.getPayload(), 200, x, 0, x.length);
                    System.arraycopy(read.getPayload(), 216, y, 0, y.length);
                    player.setX(ByteBuffer.wrap(x).getInt());
                    player.setY(ByteBuffer.wrap(y).getInt());
                }
            } catch (IOException e) {
                LOG.severe("SERVER CRASH");
                this.close();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
