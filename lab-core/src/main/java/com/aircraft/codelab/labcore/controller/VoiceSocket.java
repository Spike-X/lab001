package com.aircraft.codelab.labcore.controller;

import com.google.common.io.ByteStreams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2021-12-01
 * tomcat-websocket
 *
 * @author tao.zhang
 * @since 1.0
 */
@Slf4j
@Controller
@ServerEndpoint("/websocket/{uid}")
public class VoiceSocket {
    private static final Map<String, Session> connections = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(@PathParam("uid") String key, Session session) throws IOException {
        System.out.println(key + "连上了");
        connections.put(key, session);
        connections.get(key).getBasicRemote().sendText("连接上了");
    }

    @OnMessage
    public void onMessage(@PathParam("uid") String key, InputStream inputStream) throws IOException {
        System.out.println(key + "连上了onMessage");
        byte[] targetArray = ByteStreams.toByteArray(inputStream);
        ByteBuffer bufferByte = ByteBuffer.wrap(targetArray);
        connections.get(key).getBasicRemote().sendBinary(bufferByte);
    }

    @OnClose
    public void onClose(@PathParam("uid") String key) {
        System.out.println(key + "断开");
        connections.remove(key);
    }

    @OnError
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }
}
