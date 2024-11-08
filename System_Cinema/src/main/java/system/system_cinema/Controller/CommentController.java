package system.system_cinema.Controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import system.system_cinema.DTO.ApiResponse;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import system.system_cinema.Service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {
    final CommentService commentService;
    @PostMapping("/add/{userId}")
    public ApiResponse<CommentResponse> addComment(@PathVariable String userId, @RequestBody CommentRequest commentRequest) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Comment added successfully")
                    .data(commentService.addComment(userId, commentRequest))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/get/{commentId}")
    public ApiResponse<CommentResponse> getCommentById(@PathVariable String commentId) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Successful")
                    .data(commentService.getCommentById(commentId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @GetMapping("/movie/{movieId}")
    public ApiResponse<List<CommentResponse>> getCommentsByMovie(@PathVariable String movieId) {
        try {
            return ApiResponse.<List<CommentResponse>>builder()
                    .message("Successful")
                    .data(commentService.getCommentsByMovie(movieId))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<List<CommentResponse>>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @PutMapping("/update/{commentId}")
    public ApiResponse<CommentResponse> updateComment(@PathVariable String commentId, @RequestParam String newContent, @RequestParam int rate) {
        try {
            return ApiResponse.<CommentResponse>builder()
                    .message("Comment updated successfully")
                    .data(commentService.updateComment(commentId, newContent, rate))
                    .build();
        } catch (Exception e) {
            return ApiResponse.<CommentResponse>builder()
                    .error(e.getMessage())
                    .build();
        }
    }

    @DeleteMapping("/delete/{commentId}")
    public ApiResponse<?> deleteComment(@PathVariable String commentId) {
        try {
            commentService.deleteComment(commentId);
            return ApiResponse.builder()
                    .message("Comment deleted successfully")
                    .build();
        } catch (Exception e) {
            return ApiResponse.builder()
                    .error(e.getMessage())
                    .build();
        }
    }
}