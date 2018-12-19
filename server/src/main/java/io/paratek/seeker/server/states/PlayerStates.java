package io.paratek.seeker.server.states;

import io.paratek.seeker.net.Opcodes;
import io.paratek.seeker.net.Packet;
import io.paratek.seeker.scene.entity.Player;
import io.paratek.seeker.server.connect.ServerConnection;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class PlayerStates {

    private static final Logger LOG = Logger.getLogger(PlayerStates.class.getName());

    public static final int MAX_PLAYERS = 10;

    private final ConcurrentHashMap<Player, ServerConnection> activePlayers = new ConcurrentHashMap<>();

    public ConcurrentHashMap<Player, ServerConnection> getActivePlayers() {
        return activePlayers;
    }

    public boolean logoutPlayer(final Player player) {
        if (this.activePlayers.containsKey(player)) {
            final ServerConnection con = this.activePlayers.get(player);
            try {
                con.write(new Packet(Opcodes.LOGOUT, this.createLogoutResponse(player)));
            } catch (IOException e) {
                for (Map.Entry<Player, ServerConnection> entry : this.activePlayers.entrySet()) {
                    if (!entry.getKey().equals(player)) {
                        try {
                            entry.getValue().write(new Packet(Opcodes.LOGOUT, player.getName().getBytes()));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
            LOG.info("Player " + player.getName() + " logged out!");
            con.close();
            this.activePlayers.remove(player);
        }
        return true;
    }

    public boolean loginPlayer(final Player player, final ServerConnection connection) {
        try {
            connection.write(new Packet(Opcodes.LOGIN_SUCCESSFUL, player.getName().getBytes()));
            this.activePlayers.put(player, connection);

            for (Map.Entry<Player, ServerConnection> entry : this.activePlayers.entrySet()) {
                if (!entry.getKey().equals(player)) {
                    entry.getValue().write(new Packet(Opcodes.ADD_PLAYER, player.getName().getBytes()));
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] createLogoutResponse(final Player player) {
        final byte[] bytes = new byte[200];
        final String playerName = player.getName();
        System.arraycopy(playerName.getBytes(), 0, bytes, 0, playerName.getBytes().length);
        return bytes;
    }

}
