package comments.app.sse;

//import comments.app.examplemodule.Comment;

import comments.app.components.comment.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.messaging.support.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@Slf4j
@RestController
public class SseController {

//    private final Map<String, SseEmitter> sses  = new ConcurrentHashMap();

//    @GetMapping("/comments/getnew")
//    public SseEmitter example() {
//
//        SseEmitter sseEmitter = new SseEmitter();
////        sses.put()
//
//        return sseEmitter;
//    }

//    private Map<String, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    private final List<SseEmitter> sseEmitterList = Collections.synchronizedList(new ArrayList<>());

    @GetMapping("/comments/getnew")
    public SseEmitter eventEmitter() throws IOException {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitterList.add(sseEmitter);
//        UUID guid = UUID.randomUUID();
//        sseEmitters.put(guid.toString(), sseEmitter);
//        sseEmitter.send(SseEmitter.event().name("GUI_ID").data(guid));
        sseEmitter.onError(se -> {
            log.info("SSE ERRPOR");
            sseEmitterList.remove(sseEmitter);
        });
//        sseEmitter.onCompletion(() -> sseEmitters.remove(guid.toString()));
//        sseEmitter.onTimeout(() -> sseEmitters.remove(guid.toString()));
        return sseEmitter;
    }

    //    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    @RabbitListener(queues = "${rabbitmq.queues.mongodb}")
    public void consumer(Comment message) {


//        log.info(message.getPayload().toString());
        log.info("Consumed : {} from Queue : {}",
                message, "notification");
        for (SseEmitter sseEmitter : this.sseEmitterList) {
            log.info("emmiter about to send.");
            try {
//                SseEmitter.SseEventBuilder event = SseEmitter.event().data(message);

                sseEmitter.send(SseEmitter.event().data(message));
            } catch (IOException e) {
                log.error("exception while sending message");
            }
        }
    }
}
