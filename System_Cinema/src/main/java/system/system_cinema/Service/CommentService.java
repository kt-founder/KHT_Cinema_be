package system.system_cinema.Service;

import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;

import java.util.List;

public interface CommentService {
    CommentResponse addComment(String userId, CommentRequest commentRequest);
    CommentResponse getCommentById(String commentId);
    List<CommentResponse> getCommentsByMovie(String movieId);
    CommentResponse updateComment(String commentId, String newContent, int rate);
    void deleteComment(String commentId);
}
