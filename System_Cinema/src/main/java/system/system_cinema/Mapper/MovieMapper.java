package system.system_cinema.Mapper;

import org.springframework.stereotype.Component;
import system.system_cinema.DTO.Request.MovieRequest;
import system.system_cinema.DTO.Response.MovieResponse;
import system.system_cinema.Model.Movie;

@Component
public class MovieMapper {

    public MovieResponse toMovieResponse(Movie movie) {
        return MovieResponse.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .genre(movie.getGenre())
                .description(movie.getDescription())
                .director(movie.getDirector())
                .releaseDate(movie.getReleaseDate())
                .image(movie.getImage())
                .isActive(movie.isActive())
                .build();
    }

    public Movie toMovie(MovieRequest movieRequest) {
        return Movie.builder()
                .title(movieRequest.getTitle())
                .genre(movieRequest.getGenre())
                .description(movieRequest.getDescription())
                .director(movieRequest.getDirector())
                .releaseDate(movieRequest.getReleaseDate())
                .image(movieRequest.getImage())
                .isActive(movieRequest.isActive())
                .build();
    }
}
