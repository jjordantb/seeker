package io.paratek.seeker.client;

import io.paratek.seeker.client.core.Display;
import io.paratek.seeker.client.core.GameEngine;
import io.paratek.seeker.client.core.RenderingSurface;
import io.paratek.seeker.scene.entity.Player;

import java.util.Random;

public class Application {

    public static void main(String[] args) {
        final RenderingSurface renderingSurface = new RenderingSurface();
        final Display display = new Display(765, 503, renderingSurface);
        final GameEngine engine = new GameEngine(renderingSurface);
        display.init();
        new Thread(engine).start();

        Game.LOGIN_HANDLER.login(new Player("" + new Random().nextInt(100)));
    }

}
