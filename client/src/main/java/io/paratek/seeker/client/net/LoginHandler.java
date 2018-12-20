package io.paratek.seeker.client.net;

import io.paratek.seeker.client.Game;
import io.paratek.seeker.net.Opcodes;
import io.paratek.seeker.net.Packet;
import io.paratek.seeker.scene.entity.Player;

import java.io.IOException;

public class LoginHandler {

    public boolean login(final Player player) {
        try {
            Game.CLIENT_CONNECTION.write(new Packet(Opcodes.LOGIN_REQUEST, player));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}
