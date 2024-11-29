package org.aldv.service.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.aldv.entity.Movie;
import org.aldv.exception.InvalidParseYearException;
import org.aldv.exception.MovieNotFoundException;
import org.aldv.exception.RatingInvalidException;
import org.aldv.repository.MovieRepository;
import org.aldv.service.IMovieService;

import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.List;

@ApplicationScoped
public class MovieService implements IMovieService {

    @Inject
    MovieRepository repository;

    @Override
    public Movie findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new MovieNotFoundException(
                                "movie with id: " + id + " not found"
                        ));
    }

    public void checksomepoints(){
        System.out.println("checksomepoints");
    }

    public void anotherPoint(){
        System.out.println("anotherPoint");
    }

    @Override
    public List<Movie> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Movie save(Movie movie) {
        return repository.save(movie);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        repository.delete(id);
    }

    @Override
    public Movie findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Movie> findOrderMoviesByRating() {
        return repository.findOrderMoviesByRating();
    }

    @Override
    public Long totalCountMovies() {
        return repository.countMovies();
    }

    @Override
    public List<Movie> findMovieByFiltersRatingAndReleaseYear(Double rating, String release) {
        Year parsedYear = pastYear(release);

        if (rating != null && parsedYear != null) {
            return repository.findMovieByRatingAndReleaseYear(rating, parsedYear);
        } else if (rating != null) {
            validateRating(rating);
            return repository.findByRatingGreaterThan(rating);
        } else if (parsedYear != null) {
            return repository.findMoviesByReleaseYear(parsedYear);
        } else {
            return findAll();
        }
    }

    private void validateRating(Double rating) {
        if (rating != null && (rating <= 0 || rating > 10)) {
            throw new RatingInvalidException("rating mustn't be zero and mustn't be greater 10");
        }
    }

    private Year pastYear(String release) {
        if (release == null) {
            return null;
        }

        try {
            Year parsedYear = Year.parse(release);
            if (parsedYear.isBefore(Year.of(1900)) || parsedYear.isAfter(Year.of(2100))) {
                throw new InvalidParseYearException("Year must be between 1900 and 2100: " + release);
            }
            return parsedYear;
        } catch (DateTimeParseException e) {
            throw new InvalidParseYearException("Invalid release year format " + release);
        }
    }
}

