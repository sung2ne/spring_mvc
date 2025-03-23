package com.example.spring.post;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/posts")
public class PostController {

    @Autowired
    PostService postService;

    // 파일 업로드 경로
    private final String uploadPath = "C:/upload/post";

    // 게시글 등록 (화면, GET)
    @GetMapping("/create")
    public String create() {
        return "post/create";
    }

    // 게시글 등록 (처리, POST)
    @PostMapping("/create")
    public String createPost(PostDto post, RedirectAttributes redirectAttributes) {
        try {
            // 업로드 파일
            MultipartFile uploadFile = post.getUploadFile();

            // 업로드 파일이 존재하는 경우
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename();
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName;

                // 업로드 디렉토리가 없으면 생성
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                // 파일 저장
                File fileToUpload = new File(uploadPath + File.separator + fileName);
                uploadFile.transferTo(fileToUpload);

                // 파일 정보 설정
                post.setFileName(fileName);
                post.setOriginalFileName(originalFileName);
            }

            // 게시글 저장
            boolean created = postService.create(post);

            if (created) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 등록되었습니다.");
                return "redirect:/posts/";
            }

            redirectAttributes.addFlashAttribute("errorMessage", "게시글 등록에 실패했습니다.");
            return "redirect:/posts/create";
        } catch (IOException | IllegalStateException e) {
            logger.error("파일 업로드 오류: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
            return "redirect:/posts/create";
        }
    }

    // 게시글 목록 (화면, GET)
    @GetMapping("")
    public String listGet(
        @RequestParam(value = "page", defaultValue = "1") int currentPage, 
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchKeyword,
        Model model
    ) {
        int listCountPerPage = 10;  // 한 페이지에서 불러올 게시글 수
        int pageCountPerPage = 5;   // 한 페이지에서 보여질 페이지 수
        Map<String, Object> result = postService.list(currentPage, listCountPerPage, pageCountPerPage, searchType, searchKeyword);
        model.addAttribute("posts", result.get("posts"));
        model.addAttribute("pagination", result.get("pagination"));
        model.addAttribute("searchType", result.get("searchType"));
        model.addAttribute("searchKeyword", result.get("searchKeyword"));
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

        if (postService.update(post)) {
            redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
            return "redirect:/posts/" + id;
        }
        
        redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
        return "redirect:/posts/" + id + "/update";
    }

    // 게시글 삭제 (처리, POST)
    @PostMapping("/{id}/delete")
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
