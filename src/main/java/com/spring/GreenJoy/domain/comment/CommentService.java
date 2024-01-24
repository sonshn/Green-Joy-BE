package com.spring.GreenJoy.domain.comment;

import com.spring.GreenJoy.domain.comment.dto.CreateCommentRequest;
import com.spring.GreenJoy.domain.comment.dto.DeleteCommentRequest;
import com.spring.GreenJoy.domain.comment.dto.GetCommentListResponse;
import com.spring.GreenJoy.domain.comment.dto.UpdateCommentRequest;
import com.spring.GreenJoy.domain.comment.entity.Comment;
import com.spring.GreenJoy.domain.post.PostRepository;
import com.spring.GreenJoy.domain.post.entity.Post;
import com.spring.GreenJoy.domain.user.UserRepository;
import com.spring.GreenJoy.domain.user.entity.User;
import com.spring.GreenJoy.global.common.NanoId;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 댓글 생성
    public Long createComment(CreateCommentRequest createCommentRequest) {
        User user = userRepository.findById(NanoId.of(createCommentRequest.userId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        Post post = postRepository.findById(createCommentRequest.postId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));


        Comment comment = commentRepository.save(Comment.builder()
                .content(createCommentRequest.content())
                .user(user)
                .post(post)
                .build());

        return comment.getCommentId();
    }

    // 댓글 전체 조회(페이징)
    public Page<GetCommentListResponse> getCommentList(Long postId, Pageable pageable) {
        Page<Comment> commentPage = commentRepository.findByPost_PostId(postId, pageable);

        return commentPage.map(comment -> GetCommentListResponse.builder()
                .content(comment.getContent())
                .writer(comment.getUser().getName())
                .updatedAt(comment.getUpdatedAt())
                .build());
    }

    // 댓글 수정
    @Transactional
    public Long updateComment(Long commentId, UpdateCommentRequest updateCommentRequest) {
        Comment comment = commentRepository.findByUser_UserId(NanoId.of(updateCommentRequest.userId()))
                .orElseThrow(() -> new IllegalArgumentException("댓글을 작성한 유저가 아닙니다."));

        comment.setContent(updateCommentRequest.content());

        commentRepository.save(comment);

        return comment.getCommentId();
    }

    // 댓글 삭제
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글입니다."));

        commentRepository.deleteById(comment.getCommentId());
    }

    // TODO : 게시글을 작성한 유저인지 확인하는 로직

}
