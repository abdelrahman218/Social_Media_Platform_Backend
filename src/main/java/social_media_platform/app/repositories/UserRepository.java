package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.User;

public interface UserRepository extends JpaRepository<User, String>{}
