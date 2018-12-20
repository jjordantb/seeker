package io.paratek.seeker.scene.entity;


import java.io.Serializable;
import java.util.Objects;

public class Player extends Entity implements Serializable {

    private final String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' + " " +
                "x=" + super.getX() + " " +
                "y=" + super.getY() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equals(getName(), player.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

}
