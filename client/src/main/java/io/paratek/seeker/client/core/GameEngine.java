package io.paratek.seeker.client.core;

import com.sun.deploy.util.ArrayUtil;
import io.paratek.seeker.client.Game;
import io.paratek.seeker.net.Opcodes;
import io.paratek.seeker.net.Packet;
import io.paratek.seeker.scene.entity.Player;
import org.apache.commons.lang3.ArrayUtils;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.ByteBuffer;

public class GameEngine implements Runnable {

    private static final int TICKS_PER_SECOND = 50;
    private static final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    private static final int MAX_FRAMESKIP = 10;

    private long interpolation = 0;
    private int ticks = 0;
    private boolean running = true;

    private final RenderingSurface surface;

    public GameEngine(RenderingSurface surface) {
        this.surface = surface;
    }

    public void run() {
        long nextGameTick = System.currentTimeMillis();
        int loopCount;
        while (this.isRunning()) {
            this.ticks++;
            loopCount = 0;
            while (System.currentTimeMillis() > nextGameTick && loopCount < MAX_FRAMESKIP) {
                this.gameTick();
                nextGameTick += SKIP_TICKS;
                loopCount++;
            }
            this.interpolation = (System.currentTimeMillis() + SKIP_TICKS - nextGameTick) / SKIP_TICKS;
            this.renderTick();
        }
    }

    private boolean isRunning() {
        return this.running;
    }

    private void gameTick() {
        boolean updatePosition = false;
        // Process Input
        final KeyEvent keyEvent = Game.KEYBOARD.getKeyEvents().poll();
        if (keyEvent != null) {
            final Player local = Game.SCENE_GRAPH.localPlayer;
            if (keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                local.setY(local.getY() + 10);
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                local.setY(local.getY() - 10);
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_LEFT) {
                local.setX(local.getX() - 10);
            } else if (keyEvent.getKeyCode() == KeyEvent.VK_RIGHT) {
                local.setX(local.getX() + 10);
            }
            updatePosition = true;
        }
        // Update Server
        if (updatePosition) {
            final Player local = Game.SCENE_GRAPH.localPlayer;
            final byte[] buffer = ArrayUtils.addAll(ByteBuffer.allocate(8).putInt(local.getX()).array(), ByteBuffer.allocate(8).putInt(local.getY()).array());
            try {
                Game.CLIENT_CONNECTION.write(new Packet(Opcodes.UPDATE_PLAYER_LOCATION, buffer));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void renderTick() {
        this.surface.update(this.interpolation);
    }

    public void shutdown() {
        this.running = false;
        // Shutdown procedure
    }

}
