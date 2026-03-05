package com.houssam.video_service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TmdbVideoResponseDTO {

    private String id;
    private String title;
    private String overview;

    @JsonProperty("poster_path")
    private String posterPath;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double voteAverage;

    private Integer runtime;

    private Credits credits;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Credits {
        private java.util.List<CastMember> cast;
        private java.util.List<CrewMember> crew;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CastMember {
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CrewMember {
        private String name;
        private String job;
    }
}
