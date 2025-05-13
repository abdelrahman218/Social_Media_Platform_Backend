package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.Friend;
import social_media_platform.app.models.JoinedIds.FriendId;

public interface FriendRepository extends JpaRepository<Friend, FriendId>{}
