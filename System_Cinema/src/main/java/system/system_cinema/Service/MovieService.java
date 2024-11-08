package system.system_cinema.Service;

import system.system_cinema.DTO.Request.MovieRequest;
import system.system_cinema.DTO.Response.MovieResponse;

import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovies();
    MovieResponse getMovieById(String id);
    MovieResponse createMovie(MovieRequest movieRequest);
    MovieResponse updateMovie(String id, MovieRequest movieRequest);
    void deleteMovie(String id);
    List<MovieResponse> searchMovie(String keyWords);
}
