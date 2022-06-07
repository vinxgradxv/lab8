package server;

import commands.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class SendManager {

    private InetAddress clientAddress;
    private int port;
    private DatagramChannel channel;
    private final int limitSend = 100;
    DatagramSocket socket;
    private byte[] buf;
    org.apache.logging.log4j.Logger logger = LogManager.getLogger("server.SendManager");


    public SendManager(DatagramSocket socket, InetAddress clientAddress, int port) {
        this.socket = socket;
        this.clientAddress = clientAddress;
        this.port = port;
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        byte[] outMess = out.toByteArray();
        out.close();
        os.close();
        logger.info("Объект сериализован");
        return outMess;
    }

    public void sendResponse(Response result) throws IOException {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(clientAddress, port);
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        logger.info("Параметры канала заданы");
        buf = serialize(result);
        byte[] bufSize = serialize(buf.length);
        ByteBuffer byteBufferSize = ByteBuffer.wrap(bufSize);
        channel.send(byteBufferSize, inetSocketAddress);
        logger.info("Длина ответа отправлена");
        ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
        channel.send(byteBuffer, inetSocketAddress);
        logger.info("Ответ отправлен");
    }

}
