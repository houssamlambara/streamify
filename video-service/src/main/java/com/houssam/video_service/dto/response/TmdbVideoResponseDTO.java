package com.houssam.video_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TmdbVideoResponseDTO {

    private String id;
    private String titre;
    private String description;

    @JsonProperty("poster_path")
    private String posterPath; // → thumbnailUrl = "https://image.tmdb.org/t/p/w500" + posterPath

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double voteAverage;

    private Integer duration;
}
