package com.example.spring.post;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PostController {

    @Autowired
    PostService postService;

    // 게시글 등록 (화면, GET)
    @RequestMapping(value = "/posts/create", method = RequestMethod.GET)
    public String create() {
        return "post/create";
    }

    // 게시글 등록 (처리, POST)
    @RequestMapping(value = "/posts/create", method = RequestMethod.POST)
    public String createPost(PostDto post, RedirectAttributes redirectAttributes) {
        boolean created = postService.create(post);

        if (created) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
            return "redirect:/posts";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록에 실패했습니다.");
        return "redirect:/post/create";
    }

    // 게시글 목록 (화면, GET)
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String listGet(Model model) {
        List<PostDto> posts = postService.list();
        model.addAttribute("posts", posts);
        return "post/list";
    }

    // 게시글 보기 (화면, GET)
    @RequestMapping(value = "/posts/{id}", method = RequestMethod.GET)
    public String readGet(@PathVariable("id") int id, Model model) {
        PostDto post = postService.read(id);
        model.addAttribute("post", post);
        return "post/read";
    }

    // 게시글 수정 (화면, GET)
    @RequestMapping(value = "/posts/{id}/update", method = RequestMethod.GET)
    public String updateGet(@PathVariable("id") int id, Model model) {
        PostDto post = postService.read(id);
        model.addAttribute("post", post);
        return "post/update";
    }

    // 게시글 수정 (처리, POST)
    @RequestMapping(value = "/posts/{id}/update", method = RequestMethod.POST)
    public String updatePost(@PathVariable("id") int id, PostDto post, RedirectAttributes redirectAttributes) {
        post.setId(id);

        if (postService.update(post)) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
            return "redirect:/posts/" + id;
        }
        
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
        return "redirect:/posts/" + id + "/update";
    }

    // 게시글 삭제 (처리, POST)
    @RequestMapping(value = "/posts/{id}/delete", method = RequestMethod.POST)
    public String deletePost(@PathVariable("id") int id, PostDto posts, RedirectAttributes redirectAttributes) {
        posts.setId(id);
        boolean deleted = postService.delete(posts);

        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
            return "redirect:/posts";
        }

        redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다.");
        return("redirect:/posts/" + id);
    }
}
