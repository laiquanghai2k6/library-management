package controller;

import model.Rating;
import service.RatingService;

import java.util.List;
import java.util.UUID;

public class RatingController {
    private final RatingService ratingService;

    public RatingController() {
        this.ratingService = new RatingService();
    }

    public List<Rating> getAllRatings(){
        return ratingService.getAllRatings();
    }

    public List<Rating> getRatingsByDocumentId(UUID id) {
        return ratingService.getRatingsByDocumentId(id);
    }

    public boolean addRating(Rating rating) {
        return ratingService.addRating(rating);
    }

    public boolean deleteRating(int id) {
        return ratingService.deleteRating(id);
    }
}
