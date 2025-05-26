package social_media_platform.app.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.Post;
import social_media_platform.app.models.User;

public interface PostRepository extends JpaRepository<Post, Integer>{
    public List<Post> findByUser(User user);
}
