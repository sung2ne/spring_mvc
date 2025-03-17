package com.example.spring.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    @Autowired
    PostDao postDao;

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
        // 게시글 보기
        PostDto originalPost = postDao.read(post.getId());

        // 게시글이 없는 경우
        if (originalPost == null) {
            return false;
        }

        // 비밀번호가 일치하지 않는 경우
        if (!originalPost.getPassword().equals(post.getPassword())) {
            return false;
        }

        int result = postDao.update(post);
        return result > 0;
    }

    // 게시글 삭제
    public boolean delete(PostDto post) {
        // 게시글 보기
        PostDto originalPost = postDao.read(post.getId());

        // 게시글이 없는 경우
        if (originalPost == null) {
            return false;
        }

        // 비밀번호가 일치하지 않는 경우
        if (!originalPost.getPassword().equals(post.getPassword())) {
            return false;
        }

        int result = postDao.delete(post.getId());
        return result > 0;
    }
}
