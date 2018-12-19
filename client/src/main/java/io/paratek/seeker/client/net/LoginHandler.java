package io.paratek.seeker.client.net;

import io.paratek.seeker.client.Game;
import io.paratek.seeker.net.Opcodes;
import io.paratek.seeker.net.Packet;

import java.io.IOException;

public class LoginHandler {

    public void login(String name, String pass) {
        final byte[] bytes = new byte[400];
        System.arraycopy(name.getBytes(), 0, bytes, 0, name.getBytes().length);
        System.arraycopy(pass.getBytes(), 0, bytes, 200, pass.getBytes().length);
        final Packet packet = new Packet(Opcodes.LOGIN_REQUEST, bytes);
        try {
            Game.CLIENT_CONNECTION.write(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
