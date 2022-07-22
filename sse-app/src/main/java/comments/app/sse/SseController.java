package comments.app.sse;

//import comments.app.examplemodule.Comment;

import comments.app.components.comment.*;
import lombok.extern.slf4j.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.*;

import java.io.*;
import java.util.*;

@Slf4j
@RestController
public class SseController {

    //we can add emitters in a map to find only a specific one
    private final List<SseEmitter> sseEmitterList = Collections.synchronizedList(new ArrayList<>());

    @GetMapping("/view-api/v1/comments/sse")
    public SseEmitter eventEmitter() throws IOException {

        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        sseEmitterList.add(sseEmitter);
        sseEmitter.onError(se -> {
            log.info("SSE ERRPOR");
            //todo fix
            sseEmitterList.remove(sseEmitter);
        });
        sseEmitter.onCompletion(() -> sseEmitterList.remove(sseEmitter));
        sseEmitter.onTimeout(() -> sseEmitterList.remove(sseEmitter));

        return sseEmitter;
    }


    @RabbitListener(queues = "${rabbitmq.queues.view}")
    public void consumer(Comment message) {

        if (message.getName() == null || message.getName().isEmpty()){
            message.setName("Guest");
        }

        log.info("Consumed : {} from Queue : {}",
                message, "view");

        log.info("emmiter list size: " + this.sseEmitterList.size());
        for (SseEmitter sseEmitter : this.sseEmitterList) {
            log.info("emmiter about to send.");
            try {
//                SseEmitter.SseEventBuilder event = SseEmitter.event().data(message);

                sseEmitter.send(SseEmitter.event().data(message));
                log.info("Sent message with: "  + sseEmitter.hashCode());
            } catch (IOException e) {
                sseEmitter.completeWithError(e);
                log.error("exception while sending message");
            }
        }
    }
}
