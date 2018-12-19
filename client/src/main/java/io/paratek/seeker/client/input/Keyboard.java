package io.paratek.seeker.client.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Keyboard implements KeyListener {

    private final ConcurrentLinkedQueue<KeyEvent> keyEvents = new ConcurrentLinkedQueue<>();

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT
                || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            this.keyEvents.add(e);
        }
    }

    public void keyReleased(KeyEvent e) {

    }

    public ConcurrentLinkedQueue<KeyEvent> getKeyEvents() {
        return keyEvents;
    }

}
