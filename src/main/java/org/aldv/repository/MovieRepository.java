package org.aldv.repository;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.aldv.entity.Movie;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MovieRepository{

    @PersistenceContext
    EntityManager entityManager;

    public Movie save(Movie movie){
        if(movie.getId() == null){
            entityManager.persist(movie);
        }
        movie = entityManager.merge(movie);
        return movie;
    }

    public Optional<Movie> findById(Integer id){
        return Optional.ofNullable(entityManager.find(Movie.class, id));
    }

    public List<Movie> findAll(){
        return entityManager.createQuery("select m from Movie m", Movie.class)
                .getResultList();
    }

    public void delete(Integer id){
        Movie movie = entityManager.find(Movie.class, id);
        if(movie != null){
            entityManager.remove(movie);
        }
    }

    public List<Movie> findByRatingGreaterThan(Double rating){
        return entityManager.createQuery("select m from Movie m where m.rating >= :rating", Movie.class)
                .setParameter("rating", rating)
                .getResultList();
    }

    public List<Movie> findOrderMoviesByRating(){
        return entityManager.createQuery("select m from Movie m order by m.rating asc ", Movie.class)
                .getResultList();
    }

    public List<Movie> findMoviesByReleaseYear(Year release){
        return entityManager.createQuery("select m from Movie m where m.releaseYear = :release", Movie.class)
                .setParameter("release", release)
                .getResultList();
    }

    public List<Movie> findMovieByRatingAndReleaseYear(Double rating, Year release){
        return entityManager.createQuery("select m from Movie m where m.rating >= :rating " +
                "and m.releaseYear = :release", Movie.class)
                .setParameter("rating", rating).setParameter("release", release).getResultList();
    }

    public Long countMovies(){
        return entityManager.createQuery("select count(m) from Movie m", Long.class)
                .getSingleResult();
    }

    public Movie findByName(String name){
        return entityManager.createQuery("select m from Movie m where m.name = :name", Movie.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
