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

    public void logoutPlayer(final Player player) {
        if (this.activePlayers.containsKey(player)) {
            final ServerConnection con = this.activePlayers.get(player);
            for (Map.Entry<Player, ServerConnection> entry : this.activePlayers.entrySet()) {
                if (!entry.getKey().equals(player)) {
                    try {
                        entry.getValue().write(new Packet(Opcodes.LOGOUT, player));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
            LOG.info("Player " + player.getName() + " logged out!");
            con.close();
            this.activePlayers.remove(player);
        }
    }

    public boolean loginPlayer(final Player player, final ServerConnection connection) {
        try {
            connection.write(new Packet(Opcodes.LOGIN_SUCCESSFUL, player));
            this.activePlayers.put(player, connection);
            for (Map.Entry<Player, ServerConnection> entry : this.activePlayers.entrySet()) {
                if (!entry.getKey().equals(player)) {
                    entry.getValue().write(new Packet(Opcodes.ADD_PLAYER, player));
                }
                connection.write(new Packet(Opcodes.ADD_PLAYER, entry.getKey()));
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Pushes an updated player location to all players, in reality this should only happen if the two players are in
     * the same region.
     * @param player
     * @return
     */
    public void pushUpdatedPlayer(final Player player) {
        for (Map.Entry<Player, ServerConnection> entry : this.activePlayers.entrySet()) {
            try {
                entry.getValue().write(new Packet(Opcodes.UPDATE_PLAYER_LOCATION, player));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        final ServerConnection connection = this.activePlayers.get(player);
        this.activePlayers.remove(player);
        this.activePlayers.put(player, connection);
    }

}
