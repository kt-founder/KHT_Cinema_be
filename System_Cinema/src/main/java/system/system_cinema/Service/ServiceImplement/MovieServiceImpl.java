package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.MovieRequest;
import system.system_cinema.DTO.Response.MovieResponse;
import system.system_cinema.Mapper.MovieMapper;
import system.system_cinema.Model.Movie;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Service.MovieService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final MovieMapper movieMapper;

    @Override
    public List<MovieResponse> getAllMovies() {
        return movieRepository.findAll().stream()
                .map(movieMapper::toMovieResponse)
                .collect(Collectors.toList());
    }

    @Override
    public MovieResponse getMovieById(String id) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));
        return movieMapper.toMovieResponse(movie);
    }

    @Override
    public MovieResponse createMovie(MovieRequest movieRequest) {
        Movie movie = movieMapper.toMovie(movieRequest);
        Movie savedMovie = movieRepository.save(movie);
        return movieMapper.toMovieResponse(savedMovie);
    }

    @Override
    public MovieResponse updateMovie(String id, MovieRequest movieRequest) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        movie.setTitle(movieRequest.getTitle());
        movie.setGenre(movieRequest.getGenre());
        movie.setDescription(movieRequest.getDescription());
        movie.setDirector(movieRequest.getDirector());
        movie.setReleaseDate(movieRequest.getReleaseDate());
        movie.setImage(movieRequest.getImage());
        movie.setActive(movieRequest.isActive());

        Movie updatedMovie = movieRepository.save(movie);
        return movieMapper.toMovieResponse(updatedMovie);
    }

    @Override
    public void deleteMovie(String id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Movie not found"));
        movie.setActive(!movie.isActive());
        movieRepository.save(movie);
    }
}
