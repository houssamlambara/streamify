package com.houssam.video_service.repository;

import com.houssam.video_service.model.Video;
import com.houssam.video_service.enums.VideoCategory;
import com.houssam.video_service.enums.VideoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<Video, String> {
    List<Video> findByTitleContainingIgnoreCase(String title);

    List<Video> findByType(VideoType type);

    List<Video> findByCategory(VideoCategory category);
}
