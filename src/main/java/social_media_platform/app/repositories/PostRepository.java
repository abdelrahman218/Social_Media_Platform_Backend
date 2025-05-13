package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{}
