package com.houssam.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "watch_history")
public class WatchHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String videoId;

    private LocalDateTime watchedAt;

    // Temps de progression en secondes
    private Integer progressTime;

    // true si la vidéo a été regardée jusqu'à la fin
    private Boolean completed;

    @PrePersist
    public void onCreate() {
        watchedAt = LocalDateTime.now();
    }
}

