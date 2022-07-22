package comments.app.view;

import org.springframework.data.mongodb.repository.*;

public interface CommentRepository extends MongoRepository<MongodbComment, String> {


}
