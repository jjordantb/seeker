package io.paratek.seeker.client.core;

import io.paratek.seeker.client.Game;

import javax.swing.*;
import java.awt.*;

public class Display {

    private final int width, height;
    private final RenderingSurface renderingSurface;
    private final JFrame frame = new JFrame("Seeker");

    public Display(int width, int height, RenderingSurface renderingSurface) {
        this.width = width;
        this.height = height;
        this.renderingSurface = renderingSurface;
    }

    public void init() {
        this.frame.addKeyListener(Game.KEYBOARD);

        this.frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.frame.getContentPane().setPreferredSize(new Dimension(this.width, this.height));
        this.renderingSurface.setPreferredSize(new Dimension(this.width, this.height));
        this.frame.setLayout(new BorderLayout());
        this.frame.add(renderingSurface, BorderLayout.CENTER);
        this.frame.pack();
        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);
    }

}
