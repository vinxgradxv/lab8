package utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;

public class ReceiveManager {
    private final DatagramSocket socket;
    private DatagramPacket inPacket;
    private byte[] buff = new byte[2048];
    private byte[] resultBuff;

    public ReceiveManager(DatagramSocket socket) {
        this.socket = socket;
    }

    public synchronized Response receiveMessage() throws IOException, ClassNotFoundException {
        setTimeout(10000);
        DatagramPacket datagramPacketSize = new DatagramPacket(buff, buff.length);
        socket.receive(datagramPacketSize);
        Integer size = (Integer) deserialize(datagramPacketSize.getData());
        resultBuff = new byte[size];
        DatagramPacket datagramPacket = new DatagramPacket(resultBuff, resultBuff.length);
        socket.receive(datagramPacket);
        Response commandResult = (Response) deserialize(datagramPacket.getData());
        return commandResult;
    }

    public Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public void setTimeout(int milisek) throws SocketException {
        socket.setSoTimeout(milisek);
    }
}
