package com.tavisca.chat;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/endpoint")
public class ChatWebSocket
{
    private ChatHelper chatHelper;
    public ChatWebSocket()
    {
        this.chatHelper = new ChatHelper();
    }
    @OnOpen
    public void onOpen(Session session)
    {
         System.out.println("Open connection: "+session.getId());
         ChatHelper.sessionMap.put(session.getId(),session);
    }

    @OnClose
    public void onClose(Session session)
    {
        System.out.println("Close connection: "+session.getId());
        ChatHelper.sessionMap.remove(session.getId());
    }
    //create a thread which sends messages after every 10 seconds
    @OnMessage
    public  void onMessage(String message, Session session)
    {
        System.out.println("Received message from: "+session.getId() +" message is "+message);
        try
        {
            //session contains information then getBasic will be used to send message
            session.getBasicRemote().sendText(message.toUpperCase()); //basic way to send message to client
            chatHelper.sendMessage(message,session.getId());
        }catch (IOException e)
        {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
