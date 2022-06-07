package utils;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendManager {


    private final InetAddress address;
    private final DatagramSocket socket;
    private final int port;

    public SendManager(InetAddress address, DatagramSocket socket, int port){
        this.address = address;
        this.socket = socket;
        this.port = port;
    }

    public synchronized void sendMessage(Message message) throws IOException {
        byte[] buff = serialize(message);
        byte[] buffSize = serialize(buff.length);
        DatagramPacket sizePacket = new DatagramPacket(buffSize, buffSize.length, address, port);
        socket.send(sizePacket);
        DatagramPacket outPacket = new DatagramPacket(buff, buff.length, address, port);
        socket.send(outPacket);
    }

    private byte[] serialize(Serializable message) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(message);
        return out.toByteArray();
    }
}
