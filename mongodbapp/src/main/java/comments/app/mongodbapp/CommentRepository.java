package comments.app.mongodbapp;

import org.springframework.data.mongodb.repository.*;

public interface CommentRepository extends MongoRepository<MongodbComment, String> {


}
