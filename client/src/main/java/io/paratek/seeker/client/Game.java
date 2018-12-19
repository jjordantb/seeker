package io.paratek.seeker.client;

import io.paratek.seeker.client.input.Keyboard;
import io.paratek.seeker.client.net.ClientConnection;
import io.paratek.seeker.client.net.LoginHandler;
import io.paratek.seeker.scene.SceneGraph;

import java.io.IOException;

public class Game {

    private static final int PORT = 42069;
    private static final String IP = "localhost";

    public static final ClientConnection CLIENT_CONNECTION = new ClientConnection();

    static {
        try {
            CLIENT_CONNECTION.open(IP, PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final SceneGraph SCENE_GRAPH = new SceneGraph();
    public static final LoginHandler LOGIN_HANDLER = new LoginHandler();
    public static final Keyboard KEYBOARD = new Keyboard();

}
