package com.houssam.user_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "watchlist")
public class Watchlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false)
    private String videoId;

    private LocalDateTime addedAt;

    @PrePersist
    public void onCreate(){
        addedAt = LocalDateTime.now();
    }
}
