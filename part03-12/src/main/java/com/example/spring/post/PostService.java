package com.example.spring.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

    // 비밀번호 검증
    private boolean verifyPassword(PostDto post) {
        PostDto originalPost = postDao.read(post.getId());
        return originalPost != null && originalPost.getPassword().equals(post.getPassword());
    }

    // 게시글 등록
    public boolean create(PostDto post) {
        int result = postDao.create(post);
        return result > 0;
    }

    // 게시글 목록
    public List<PostDto> list() {
        return postDao.list();
    }

    // 게시글 보기
    public PostDto read(int id) {
        return postDao.read(id);
    }

    // 게시글 수정
    public boolean update(PostDto post) {
        // 비밀번호 검증
        if (!verifyPassword(post)) {
            return false;
        }

        int result = postDao.update(post);
        return result > 0;
    }

    // 게시글 삭제
    public boolean delete(PostDto post) {
        // 비밀번호 검증
        if (!verifyPassword(post)) {
            return false;
        }

        int result = postDao.delete(post.getId());
        return result > 0;
    }
}
