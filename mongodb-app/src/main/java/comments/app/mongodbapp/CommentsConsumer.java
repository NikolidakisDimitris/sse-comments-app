package comments.app.mongodbapp;

import comments.app.components.comment.*;
import comments.app.components.rabbitmq.*;
import lombok.extern.slf4j.*;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

@Slf4j
@Component
public class CommentsConsumer {

    public static final String QUEUE = "receiver";

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    @Value("${rabbitmq.exchanges.view}")
    private String mongodbExchange;


    @RabbitListener(queues = "${rabbitmq.queues.receiver}")
    public void consumer(Comment comment) {


        MongodbComment mongodbComment = new MongodbComment(comment);
        log.info("Consumed : {} from Queue : {}",
                comment, QUEUE);


        this.commentRepository.insert(mongodbComment);
        this.rabbitMQPublisher.publishFan(comment, mongodbExchange);
    }


}
