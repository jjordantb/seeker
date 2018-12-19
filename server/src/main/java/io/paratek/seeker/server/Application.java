package io.paratek.seeker.server;

import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        try {
            final Server server = new Server(42069);
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
