package server;

import org.apache.logging.log4j.Logger;
import utils.Message;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;

public class ReceiveManager {
    private final int defaultBufferSize = 2048;
    private final int defaultSleepTime = 500;
    private SocketAddress client;
    private DatagramSocket socket;
    private DatagramPacket inputPacket;
    private org.apache.logging.log4j.Logger logger;

    public ReceiveManager(DatagramSocket socket, Logger logger) {
        this.socket = socket;
        this.logger = logger;
    }

    public <T> T deserializeObject(byte[] buffer) {
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer);
             ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);) {
            logger.info("Объект десериализован");
            return (T) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Ошибка десериализации");
            e.printStackTrace();
        }
        return null;
    }

    public int getPort(){
        return inputPacket.getPort();
    }

    public InetAddress getAddress(){
        return inputPacket.getAddress();
    }


    public Message receiveMessage() throws IOException {
        byte[] receivingDataBuffer = new byte[2048];
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        socket.receive(inputPacket);
        logger.info("Длина сообщения получена");
        Integer size = deserializeObject(inputPacket.getData());
        receivingDataBuffer = new byte[size];
        inputPacket = new DatagramPacket(receivingDataBuffer, receivingDataBuffer.length);
        socket.receive(inputPacket);
        logger.info("Сообщение получено");
        Message mess = deserializeObject(inputPacket.getData());
        return mess;
    }
}
