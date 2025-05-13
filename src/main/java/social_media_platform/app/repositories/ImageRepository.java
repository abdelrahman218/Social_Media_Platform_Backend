package social_media_platform.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import social_media_platform.app.models.Image;

public interface ImageRepository extends JpaRepository<Image, Integer>{}
