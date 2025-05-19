package social_media_platform.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.Friend;
import social_media_platform.app.models.User;
import social_media_platform.app.models.JoinedIds.FriendId;

public interface FriendRepository extends JpaRepository<Friend, FriendId>{
    List<Friend> findByUser1AndIs_pending(User user1, boolean is_pending);
    List<Friend> findByUser2AndIs_pending(User user1, boolean is_pending);
}
