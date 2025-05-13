package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{}
