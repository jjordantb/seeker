package io.paratek.seeker.net;

import java.io.Serializable;

public class Packet implements Serializable {

    private final int op;
    private final byte[] payload;

    public Packet(int op, byte[] payload) {
        this.op = op;
        this.payload = payload;
    }

    public int getOp() {
        return op;
    }

    public byte[] getPayload() {
        return payload;
    }

}
