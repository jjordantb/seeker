package io.paratek.seeker.scene;

import io.paratek.seeker.scene.entity.Player;

import java.util.concurrent.ConcurrentHashMap;

public class SceneGraph {

    public ConcurrentHashMap<String, Player> players = new ConcurrentHashMap<>();
    public Player localPlayer = null;

}
