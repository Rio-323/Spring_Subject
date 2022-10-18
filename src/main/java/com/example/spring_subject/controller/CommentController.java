package com.example.spring_subject.controller;

import com.example.spring_subject.dto.CommentRequestDto;
import com.example.spring_subject.entity.Comment;
import com.example.spring_subject.entity.Post;
import com.example.spring_subject.repository.CommentRepository;
import com.example.spring_subject.repository.PostRepository;
import com.example.spring_subject.security.user.UserDetailsImpl;
import com.example.spring_subject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.Member;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentRepository commentRepository;
    private final CommentService commentService;
    private final PostRepository postRepository;

    @PostMapping("/auth/comment")
    public Comment createComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        Post post = postRepository.findById(commentRequestDto.getPostid()).orElseThrow(
                ()->new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, post, userDetailsImpl.getAccount ());
        return commentRepository.save(comment);
    }

    @GetMapping("/comment")
    public List<Comment> getComment(){return commentRepository.findAllByOrderByCreatedAtDesc();}


    @PutMapping("/auth/comment/{id}")
    public String updateComment(@PathVariable Long id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl ) throws Exception {
        Comment comment = this.commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다."));
        String email = comment.getAccount ().getEmail ();
        if(userDetailsImpl.getAccount().getEmail().equals(email)){
            commentService.update(id, commentRequestDto);
        }else {
            throw new Exception("사용자가 일치하지 않습니다.");
        }
        return "댓글 수정완료";
    }


    @DeleteMapping("/auth/comment/{id}")
    public String deleteComment(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws Exception {

        Comment comment = this.commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException("id가 없습니다."));
        String email = comment.getAccount ().getEmail ();
        if(userDetailsImpl.getAccount().getEmail().equals(email)){
            commentRepository.deleteById(id);
        }else {throw new Exception("사용자가 일치하지 않습니다.");
        }
        return  "댓글삭제완료";
    }

}
//    @PostMapping("/post")
//    public Post createPost(@RequestBody PostRequestDto requestDto) {
//
//// 자 , 토큰값으로 '지금' 글쓰는 사람의 멤버 아이디 가져올게요~~~
//        Member member = memberService.getInfo();
//        String nickname= member.getNickname();
//
//// 이제 레포지토리에 저장해볼게요 왜냐하면 아이디값 알아냇어요
//        Post post =new Post(requestDto,nickname);
//        postRepository.save(post);
////위까지는 db에는 정확히 들어가지만
////response는 아무 응답이 없는거처럼 보인다
//        return post;
//    }
//}
