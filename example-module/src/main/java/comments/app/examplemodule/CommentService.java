package comments.app.examplemodule;

import comments.app.components.rabbitmq.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private RabbitMQPublisher rabbitMQPublisher;

//
//    public void publishInsert(CommentRequest commentRequest){
//        this.rabbitMQPublisher.publish(commentRequest, "internal.exchange", "internal.notification.routing-key");
//    }
//
//    public void insertComment(CommentRequest commentRequest){
//        this.commentRepository.insert(new Comment(commentRequest));
//    }

    public List<Comment> getAllComments(){

        return this.commentRepository.findAll();
    }



}
