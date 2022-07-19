package comments.app.examplemodule;


import lombok.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Data
@Configuration
public class CommentConfig {

    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange;

    @Value("${rabbitmq.queues.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    //need fan for the last one (SSE)
//    @Bean
//    public TopicExchange internalTopicExchange() {
//        return new TopicExchange(this.internalExchange);
//    }
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(this.internalExchange);
    }

    @Bean
    public Queue notificationQueue(){
        return new Queue(this.notificationQueue);
    }

    @Bean
    public Binding internalToNotificationQueue(){
        return BindingBuilder
                .bind(notificationQueue())
                .to(fanoutExchange());
//                .to(internalTopicExchange())
//                .with(this.internalNotificationRoutingKey);
    }




}
