package com.sparta.curtain.controller;

import com.sparta.curtain.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    private final PostService postService;
    public HomeController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/api/user/new-post")  //메인 인덱스 페이지에서 새글 버튼을 누르면 연결되도록 설정해야함->그냥 a태그 하이퍼링크로
    public String newPostPage() { return "newPost";}

}
