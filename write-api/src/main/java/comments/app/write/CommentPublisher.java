package comments.app.write;

import comments.app.commons.comment.*;
import comments.app.commons.rabbitmq.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.scheduling.annotation.*;
import org.springframework.stereotype.*;

@Component
public class CommentPublisher {

    @Autowired
    private ReceiverTopicConfig receiverTopicConfig;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

    public void publishInsert(Comment comment) {

        this.rabbitMQPublisher.publish(comment, receiverTopicConfig.getReceiverExchange(), receiverTopicConfig.getReceiverRoutingKey());
    }
}
