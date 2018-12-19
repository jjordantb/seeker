package io.paratek.seeker.scene.entity;

public class Player extends Entity {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
