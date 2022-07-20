package comments.app.components.rabbitmq;

import com.fasterxml.jackson.databind.*;
import lombok.*;
import org.springframework.amqp.rabbit.config.*;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.support.converter.*;
import org.springframework.context.annotation.*;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Configuration
@AllArgsConstructor
public class RabbitMQConfig {

    private final ConnectionFactory connectionFactory;


    @Bean
    public AmqpTemplate ampqTemplate() {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jacksonJsonConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jacksonJsonConverter());
        return factory;
    }


    @Bean
    public MessageConverter jacksonJsonConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }
}
