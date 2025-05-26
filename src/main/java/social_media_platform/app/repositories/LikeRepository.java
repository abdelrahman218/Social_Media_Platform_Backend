package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.PostLike;

public interface LikeRepository extends JpaRepository<PostLike, Integer>{}
