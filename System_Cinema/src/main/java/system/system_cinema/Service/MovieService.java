package system.system_cinema.Service;

import org.springframework.web.multipart.MultipartFile;
import system.system_cinema.DTO.Request.MovieRequest;
import system.system_cinema.DTO.Response.MovieResponse;

import java.io.IOException;
import java.util.List;

public interface MovieService {
    List<MovieResponse> getAllMovies();
    MovieResponse getMovieById(String id);
    void createMovie(MovieRequest movieRequest, MultipartFile movieImage) throws IOException;
    void updateMovie(String id, MovieRequest movieRequest, MultipartFile movieImage) throws IOException;
    void deleteMovie(String id);
    List<MovieResponse> searchMovie(String keyWords);
    MovieResponse getMovieWithAverageRating(String id);
}
