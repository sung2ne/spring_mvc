package com.example.spring.post;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostController {
    
    @Autowired
    PostService postService;

    // 게시글 등록 (화면, GET)
    @GetMapping("/create")
    public String createGet() {
        return "post/create";
    }

    // 게시글 등록 (처리, POST)
    @PostMapping("/create")
    public String createPost(PostDto post, RedirectAttributes redirectAttributes) {
        boolean created = postService.create(post);

        // 게시글 등록 성공
        if (created) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
            return "redirect:/posts/";
        }

        // 게시글 등록 실패
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록에 실패했습니다.");
        return "redirect:/posts/create";
    }

    // 게시글 목록 (화면, GET)
    @GetMapping("")
    public String listGet(@RequestParam(value = "page", defaultValue = "1") int currentPage, Model model) {
        int listCountPerPage = 10;  // 한 페이지에서 불러올 게시글 수
        int pageCountPerPage = 5;   // 한 페이지에서 보여질 페이지 수
        Map<String, Object> result = postService.list(currentPage, listCountPerPage, pageCountPerPage);
        model.addAttribute("posts", result.get("posts"));
        model.addAttribute("pagination", result.get("pagination"));
        return "post/list";
    }

    // 게시글 보기 (화면, GET)
    @GetMapping("/{id}")
    public String readGet(@PathVariable("id") int id, Model model) {
        PostDto post = postService.read(id);
        model.addAttribute("post", post);
        return "post/read";
    }

    // 게시글 수정 (화면, GET)
    @GetMapping("/{id}/update")
    public String updateGet(@PathVariable("id") int id, Model model) {
        PostDto post = postService.read(id);
        model.addAttribute("post", post);
        return "post/update";
    }

    // 게시글 수정 (처리, POST)
    @PostMapping("/{id}/update")
    public String updatePost(@PathVariable("id") int id, PostDto post, RedirectAttributes redirectAttributes) {
        post.setId(id);
        boolean updated = postService.update(post);

        // 게시글 수정 성공
        if (updated) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
            return "redirect:/posts/" + id;
        }

        // 게시글 수정 실패
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
        return "redirect:/posts/" + id + "/update";
    }    

    // 게시글 삭제 (처리, POST)
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable int id, PostDto post, RedirectAttributes redirectAttributes) {
        post.setId(id);
        boolean deleted = postService.delete(post);

        // 게시글 삭제 성공
        if (deleted) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
            return "redirect:/posts/";
        }

        // 게시글 삭제 실패
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다.");
        return "redirect:/posts/" + id;
    }
}
