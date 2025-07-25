package controller;

import model.Rating;
import service.RatingService;

import java.util.List;

public class RatingController {
    private final RatingService ratingService;

    public RatingController() {
        this.ratingService = new RatingService();
    }

    public List<Rating> getRatingsByDocumentId(int id) {
        return ratingService.getRatingsByDocumentId(id);
    }

    public boolean addRating(Rating rating) {
        return ratingService.addRating(rating);
    }

    public boolean deleteRating(int id) {
        return ratingService.deleteRating(id);
    }
}
