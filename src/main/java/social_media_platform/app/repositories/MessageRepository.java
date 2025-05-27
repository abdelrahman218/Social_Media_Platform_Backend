package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import social_media_platform.app.models.Message;
import social_media_platform.app.models.User;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderAndReceiverOrderByTimestampAsc(User sender, User receiver);
    List<Message> findByReceiverAndSenderOrderByTimestampAsc(User receiver, User sender);

    @Query("""
        SELECT m FROM Message m
        WHERE (m.sender = :user1 AND m.receiver = :user2)
           OR (m.sender = :user2 AND m.receiver = :user1)
        ORDER BY m.timestamp ASC
    """)
    List<Message> findConversation(@Param("user1") User user1, @Param("user2") User user2);

    @Query("""
        SELECT m FROM Message m
        WHERE (m.sender = :user1 AND m.receiver = :user2)
           OR (m.sender = :user2 AND m.receiver = :user1)
        ORDER BY m.timestamp DESC
    """)
    List<Message> findLatestMessageBetween(@Param("user1") User user1, @Param("user2") User user2, Pageable pageable);
} 