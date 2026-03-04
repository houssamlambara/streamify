package com.houssam.video_service.service;


import com.houssam.video_service.dto.response.TmdbVideoResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class TmdbService {

    private final RestTemplate restTemplate;

    @Value("${tmdb.api.key}")
    private String tmdbApiKey;

    @Value("${tmdb.api.base-url}")
    private String baseUrl;

    @Value("${tmdb.api.image-base-url}")
    private String imageBaseUrl;


    public TmdbVideoResponseDTO MovieById(String tmdbId){
        String url = baseUrl + "/movie/" + tmdbId + "?api_key=" + tmdbApiKey;
        try {
            return restTemplate.getForObject(url, TmdbVideoResponseDTO.class);
        }catch (Exception e){
            log.warn("Erreur lors de la récupération des données de TMDB pour l'ID : " + tmdbId, e);
            return null;
        }
    }

    public TmdbVideoResponseDTO MovieByTitre(String titre){
        String url = baseUrl + "/search/movie?api_key=" + tmdbApiKey + "query=" + titre.replace(" ", "%20") + "&format=json";
        try {
            var response = restTemplate.getForObject(url, java.util.Map.class);
            if (response != null && response.containsKey("results")) {
                var results = (java.util.List<?>) response.get("results");
                if (!results.isEmpty()) {
                    var objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                    return objectMapper.convertValue(results.get(0), TmdbVideoResponseDTO.class);
                }
            }
        } catch (Exception e) {
            log.warn("Recherche TMDb échouée pour '{}': {}", titre, e.getMessage());
        }
        return null;
        }

        public String buildImageUrl(String posterPath) {
            if (posterPath == null || posterPath.isEmpty()) {
                return null;
            }
            return imageBaseUrl + posterPath;
        }
    }
