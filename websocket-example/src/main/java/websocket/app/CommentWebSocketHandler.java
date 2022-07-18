package websocket.app;

import com.fasterxml.jackson.databind.*;
import org.springframework.stereotype.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.*;

import java.io.*;
import java.util.*;

@Component
public class CommentWebSocketHandler extends TextWebSocketHandler {

    private final List<WebSocketSession> webSocketSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        webSocketSessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

        webSocketSessions.remove(session);
    }

//    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        for (WebSocketSession webSocketSession: webSocketSessions){
            webSocketSession.sendMessage(message);
        }

//        String payload = message.getPayload();
//
//        TypeReference<HashMap<String, String>> typeRef
//                = new TypeReference<>() {
//        };
//
//        Map<String, String> payloadMap = objectMapper.readValue(payload, typeRef);
//        session.sendMessage(new TextMessage("Hi " + payloadMap.get("user") + " how may we help you?"));
    }
}
