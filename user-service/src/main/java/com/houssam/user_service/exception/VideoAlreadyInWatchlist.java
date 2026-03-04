package com.houssam.user_service.exception;

public class VideoAlreadyInWatchlist extends RuntimeException {
    public VideoAlreadyInWatchlist(String message) {
        super(message);
    }
}
