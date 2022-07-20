package comments.app.mongodbapp;


import lombok.*;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

@Data
@Configuration
public class CommentPublishConfig {

    @Value("${rabbitmq.exchanges.mongodb}")
    private String mongodbExchange;

    @Value("${rabbitmq.queues.mongodb}")
    private String mongodbQueue;


    @Bean
    public FanoutExchange fanoutExchange() {

        return new FanoutExchange(this.mongodbExchange);
    }

    @Bean
    public Queue viewQueue() {

        return new Queue(this.mongodbQueue);
    }

    @Bean
    public Binding internalToNotificationQueue() {

        return BindingBuilder.bind(viewQueue()).to(fanoutExchange());
    }
}