package org.aldv.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.aldv.entity.Movie;
import org.aldv.service.impl.MovieService;
import org.jboss.resteasy.reactive.RestPath;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Path("/api/v1/movies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MovieResource {

    @Inject
    MovieService movieService;

    @GET
    @Path("/{id}")
    public Response findById(@PathParam("id") Integer id){
        Optional<Movie> movie = Optional.ofNullable(movieService.findById(id));
        return movie.map(Response::ok)
                .orElseGet(() -> Response.status(Response.Status.NOT_FOUND)).build();
    }

    @GET
    public Response findAll(){
        return Response.ok(movieService.findAll()).build();
    }

    @POST
    public Response create(Movie movie){
        Movie savedMovie = movieService.save(movie);
        return Response.status(Response.Status.CREATED).entity(savedMovie).build();
    }

    @Path("/{id}")
    @DELETE
    public Response delete(@PathParam("id") Integer id){
        movieService.delete(id);
        return Response.status(Response.Status.NO_CONTENT).build();
    }


    @GET
    @Path("/filter/order")
    public Response findOrderMoviesByRating(){
        return Response.status(Response.Status.OK).entity(movieService.findOrderMoviesByRating()).build();
    }

    @GET
    @Path("/filter")
    public Response findMoviesByFilters(@QueryParam("rating") Double rating,
                                        @QueryParam("release") String release){
        List<Movie> movies = movieService.findMovieByFiltersRatingAndReleaseYear(rating, release);
        return Response.status(Response.Status.OK).entity(movies).build();
    }


    @GET
    @Path("/total-count")
    public Response totalCount(){
        return Response.status(Response.Status.OK).entity(movieService.totalCountMovies()).build();
    }

    @GET
    @Path("/movie")
    public Response findMovieByName(@QueryParam("name") String name){
        return Response.status(Response.Status.OK).entity(movieService.findByName(name)).build();
    }
}
