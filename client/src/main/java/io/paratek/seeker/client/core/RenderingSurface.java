package io.paratek.seeker.client.core;

import io.paratek.seeker.client.Game;
import io.paratek.seeker.scene.entity.Player;

import javax.swing.*;
import java.awt.*;

public class RenderingSurface extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Players
        for (Player player : Game.SCENE_GRAPH.players.values()) {
            if (player != null) {
                if (player.getName().equals(Game.SCENE_GRAPH.localPlayer.getName())) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.RED);
                }
                g.fillOval(player.getX() - 5, player.getY() - 5, 10, 10);
            }
        }

    }

    public void update(long interpolation) {
        super.repaint();
    }

}
