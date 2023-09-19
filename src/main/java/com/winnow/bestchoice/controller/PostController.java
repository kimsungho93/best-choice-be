package com.winnow.bestchoice.controller;

import com.winnow.bestchoice.annotation.LoginMemberId;
import com.winnow.bestchoice.model.request.CreatePostForm;
import com.winnow.bestchoice.model.response.PostDetailRes;
import com.winnow.bestchoice.model.response.PostRes;
import com.winnow.bestchoice.service.PostService;
import com.winnow.bestchoice.type.MyPageSort;
import com.winnow.bestchoice.type.Option;
import com.winnow.bestchoice.type.PostSort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Validated
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping("/api/posts")
    public ResponseEntity<PostDetailRes> createPost(@LoginMemberId long memberId,
                                        @RequestPart("data") @Valid CreatePostForm createPostForm,
                                        @RequestPart(name = "imageFile", required = false) @Size(max = 5) List<MultipartFile> imageFiles,
                                        @RequestPart(name = "videoFile", required = false) @Size(max = 5) List<MultipartFile> videoFiles) {
        PostDetailRes postDetail = postService.createPost(createPostForm, imageFiles, videoFiles, memberId);
        return ResponseEntity.status(HttpStatus.CREATED).body(postDetail);
    }

    @PostMapping("/api/posts/{postId}/like")
    public ResponseEntity<?> likePost(@LoginMemberId long memberId, @PathVariable long postId) {
        postService.likePost(memberId, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/posts/{postId}/unlike")
    public ResponseEntity<?> unlikePost(@LoginMemberId long memberId, @PathVariable long postId) {
        postService.unlikePost(memberId, postId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/posts/{postId}/choice")
    public ResponseEntity<?> choiceOption(@LoginMemberId long memberId, @PathVariable long postId,
                                          @RequestParam Option option) {
        postService.choiceOption(memberId, postId, option);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/posts/{postId}")
    public ResponseEntity<?> deletePost(@LoginMemberId long memberId, @PathVariable long postId) {
        postService.deletePost(memberId, postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostDetailRes> getPostDetail(Authentication authentication, @PathVariable long postId) {
        return ResponseEntity.ok(postService.getPostDetail(authentication, postId));
    }

    @GetMapping("/posts")
    public ResponseEntity<Slice<PostRes>> getPosts(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "LATEST") PostSort sort) {
        return ResponseEntity.ok().body(postService.getPosts(page, size, sort));
    }

    @GetMapping("/posts/tag")
    public ResponseEntity<Slice<PostRes>> getPostsByTag(@RequestParam @Size(min = 2, max = 10) String tag,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(postService.getPostsByTag(page, size, tag));
    }

    @GetMapping("/api/posts/my")
    public ResponseEntity<Slice<PostRes>> getMyPage(@LoginMemberId long memberId,
                                       @RequestParam(defaultValue = "0") int page,
                                       @RequestParam(defaultValue = "10") int size,
                                       @RequestParam(defaultValue = "LIKES") MyPageSort sort) {
        return ResponseEntity.ok().body(postService.getMyPage(memberId, page, size, sort));
    }

    @PostMapping("/api/posts/{postId}/report")
    public ResponseEntity<?> reportPost(@LoginMemberId long memberId, @PathVariable long postId) {
        postService.reportPost(memberId, postId);
        return ResponseEntity.ok().build();
    }
}
