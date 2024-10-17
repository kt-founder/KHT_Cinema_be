package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Response.MovieResponse;
import system.system_cinema.DTO.Request.MovieRequest;

import system.system_cinema.Service.MovieService;

import java.util.List;

@RestController
@RequestMapping("/movies")
@CrossOrigin("http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MovieController {
    final MovieService movieService;

    @GetMapping("/get-all")
    public ApiResponse<List<MovieResponse>> getAllMovies() {
        try {
            return ApiResponse.<List<MovieResponse>>builder()
                    .message("Successful")
                    .data(movieService.getAllMovies())
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<MovieResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get/{id}")
    public ApiResponse<MovieResponse> getMovieById(@PathVariable String id) {
        try {
            return ApiResponse.<MovieResponse>builder()
                    .message("Successful")
                    .data(movieService.getMovieById(id))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<MovieResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PostMapping("/create")
    public ApiResponse<MovieResponse> createMovie(@RequestBody MovieRequest movieRequest) {
        try {
            return ApiResponse.<MovieResponse>builder()
                    .message("Successful")
                    .data(movieService.createMovie(movieRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<MovieResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/update/{id}")
    public ApiResponse<MovieResponse> updateMovie(@PathVariable String id, @RequestBody MovieRequest movieRequest) {
        try {
            return ApiResponse.<MovieResponse>builder()
                    .message("Successful")
                    .data(movieService.updateMovie(id, movieRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<MovieResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<?> deleteMovie(@PathVariable String id) {
        try {
            movieService.deleteMovie(id);
            return ApiResponse.builder()
                    .message("Movie deleted successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}
