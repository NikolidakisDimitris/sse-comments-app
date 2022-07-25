package comments.app.view;

import comments.app.commons.comment.*;
import lombok.extern.slf4j.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.io.*;
import java.util.*;

@Slf4j
@Controller
public class SseController {

    //we can add emitters in a map to find a specific session
    private final List<SseEmitter> sseEmitterList = Collections.synchronizedList(new ArrayList<>());

    @GetMapping("view-api/v1/comments/sse")
    public SseEmitter eventEmitter() {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitterList.add(sseEmitter);
        log.info("added emitter: {}", sseEmitter);
        log.info("added emitter (hash): {}", sseEmitter.hashCode());

        return sseEmitter;
    }


    @RabbitListener(queues = "${rabbitmq.queues.view}")
    public void consumer(Comment message) {

        if (message.getName() == null || message.getName().isEmpty()) {
            message.setName("Guest");
        }

        log.info("Consumed : {} from Queue : {}",
                message, "view");
        List<SseEmitter> brokenEmitters = new ArrayList<>();
        for (SseEmitter sseEmitter : this.sseEmitterList) {
            try {
                sseEmitter.send(SseEmitter.event().data(message));
                //catch broken pipe
            } catch (IOException e) {
                log.error("exception in emitter: {}, adding to discard", sseEmitter.hashCode());
                brokenEmitters.add(sseEmitter);
                log.error("exception while sending message", e);
            }
        }
        this.sseEmitterList.removeAll(brokenEmitters);
    }
}
