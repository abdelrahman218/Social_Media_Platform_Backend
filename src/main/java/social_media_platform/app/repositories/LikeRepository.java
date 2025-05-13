package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.Like;

public interface LikeRepository extends JpaRepository<Like, Integer>{}
