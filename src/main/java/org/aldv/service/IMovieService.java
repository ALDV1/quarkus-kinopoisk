package org.aldv.service;

import org.aldv.entity.Movie;

import java.time.Year;
import java.util.List;

public interface IMovieService {

    Movie findById(Integer id);
    List<Movie> findAll();
    Movie save(Movie movie);
    void delete(Integer id);
    Movie findByName(String name);
    List<Movie> findOrderMoviesByRating();
    Long totalCountMovies();
    List<Movie> findMovieByFiltersRatingAndReleaseYear(Double rating, String release);
}
