package comments.app.examplemodule;

import org.springframework.data.mongodb.repository.*;

public interface CommentRepository extends MongoRepository<Comment, String> {


}
