package io.paratek.seeker.net;

import java.io.Serializable;

public class Packet implements Serializable {

    private final int op;
    private final Serializable payload;

    public Packet(int op, Serializable payload) {
        this.op = op;
        this.payload = payload;
    }

    public int getOp() {
        return op;
    }

    public Serializable getPayload() {
        return payload;
    }

}
