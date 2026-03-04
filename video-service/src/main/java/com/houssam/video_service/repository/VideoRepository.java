package com.houssam.video_service.repository;

import com.houssam.video_service.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video,String> {

}
