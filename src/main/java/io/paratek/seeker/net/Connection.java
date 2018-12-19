package io.paratek.seeker.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class Connection implements Runnable {

    private static final Logger LOG = Logger.getLogger(Connection.class.getName());

    protected boolean connected = true;

    protected Socket socket;
    protected ObjectOutputStream outputStream;
    protected ObjectInputStream inputStream;

    public void accept(final Socket socket) {
        this.socket = socket;
        try {
            this.outputStream = new ObjectOutputStream(this.socket.getOutputStream());
            this.inputStream = new ObjectInputStream(this.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(this).start();
    }

    public void write(final Packet packet) throws IOException {
        this.outputStream.writeObject(packet);
    }

    public Packet read() throws IOException, ClassNotFoundException {
        return (Packet) this.inputStream.readObject();
    }

    public boolean close() {
        try {
            this.socket.close();
            this.connected = false;
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}
