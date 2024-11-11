package system.system_cinema.Service.ServiceImplement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import system.system_cinema.DTO.Request.CommentRequest;
import system.system_cinema.DTO.Response.CommentResponse;
import system.system_cinema.Mapper.CommentMapper;
import system.system_cinema.Model.Comment;
import system.system_cinema.Model.Movie;
import system.system_cinema.Model.User;
import system.system_cinema.Repository.CommentRepository;
import system.system_cinema.Repository.MovieRepository;
import system.system_cinema.Repository.UserRepository;
import system.system_cinema.Service.CommentService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentResponse addComment(String userId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Movie movie = movieRepository.findById(commentRequest.getMovieId())
                .orElseThrow(() -> new RuntimeException("Movie not found"));

        Comment parentComment = null;
        if (commentRequest.getParentCommentId() != null) {
            parentComment = commentRepository.findById(commentRequest.getParentCommentId())
                    .orElseThrow(() -> new RuntimeException("Parent comment not found"));
        }

        Comment comment = commentMapper.toComment(commentRequest, parentComment);
        comment.setUser(user);
        comment.setMovie(movie);
        comment.setRate(commentRequest.getRate());  // Thiết lập rate cho bình luận
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponse(savedComment);
    }

    @Override
    public CommentResponse getCommentById(String commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        return commentMapper.toCommentResponse(comment);
    }

    @Override
    public List<CommentResponse> getCommentsByMovie(String movieId) {
        List<Comment> comments = commentRepository.findAll().stream()
                .filter(comment -> comment.getMovie().getId().equals(movieId) && comment.getParentComment() == null)
                .collect(Collectors.toList());
        return comments.stream().map(commentMapper::toCommentResponse).collect(Collectors.toList());
    }

    @Override
    public CommentResponse updateComment(String commentId, String newContent, int rate) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.setContent(newContent);
        comment.setRate(rate);  // Cập nhật rate của bình luận
        comment.setLastUpdate();

        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toCommentResponse(updatedComment);
    }
    @Override
    public CommentResponse replyToComment(String userId, String parentCommentId, CommentRequest commentRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Comment parentComment = commentRepository.findById(parentCommentId)
                .orElseThrow(() -> new RuntimeException("Parent comment not found"));

        Comment replyComment = commentMapper.toComment(commentRequest, parentComment);
        replyComment.setUser(user);
        replyComment.setMovie(parentComment.getMovie()); // Gán cùng phim với bình luận cha

        Comment savedReplyComment = commentRepository.save(replyComment);
        return commentMapper.toCommentResponse(savedReplyComment);
    }

    @Override
    public void deleteComment(String commentId) {
        commentRepository.deleteById(commentId);
    }
}
