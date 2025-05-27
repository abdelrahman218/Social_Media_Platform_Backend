package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import social_media_platform.app.models.Message;
import social_media_platform.app.models.User;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrderByTimestampAsc(User sender, User receiver);
    List<Message> findByReceiverAndSenderOrderByTimestampAsc(User receiver, User sender);
} 