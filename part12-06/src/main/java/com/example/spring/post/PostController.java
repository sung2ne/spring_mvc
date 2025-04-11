package com.example.spring.post;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

    // 운영체제에 따라 파일 업로드 경로 설정
    public String uploadPathByOS() {
        String uploadPath = "";
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            uploadPath = "C:/upload/post";
        } else if (os.contains("mac")) {
            uploadPath = "/Users/user/upload/post";
        } else if (os.contains("nux") || os.contains("nix")) {
            uploadPath = "/opt/upload/post";
        } else {
            throw new IllegalStateException("Unsupported operating system: " + os);
        }

        return uploadPath;
    }

    // 로깅을 위한 변수
    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    // 게시글 등록 (화면, GET)
    @GetMapping("/create")
    public String createGet() {
        return "post/create";
    }

    // 게시글 등록 (처리, POST)
    @PostMapping("/create")
    public String createPost(PostDto post, RedirectAttributes redirectAttributes) {
        String uploadPath = uploadPathByOS();

        try {
            // 업로드 파일
            MultipartFile uploadFile = post.getUploadFile();

            // 업로드 파일이 존재하는 경우
            if (uploadFile != null && !uploadFile.isEmpty()) {
                String originalFileName = uploadFile.getOriginalFilename(); // 원본 파일 이름
                String fileName = UUID.randomUUID().toString() + "_" + originalFileName; // UUID로 파일 이름 생성

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
        @RequestParam(required = false) String searchType,
        @RequestParam(required = false) String searchKeyword,
        @RequestParam(value = "page", defaultValue = "1") int currentPage, 
        Model model
    ) {
        int listCountPerPage = 10;  // 한 페이지에서 불러올 게시글 수
        int pageCountPerPage = 5;   // 한 페이지에서 보여질 페이지 수

        // 게시글 목록 조회
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
        String uploadPath = uploadPathByOS();

        try {
            // 기존 게시글 정보 조회
            post.setId(id);
            PostDto originalPost = postService.read(post.getId());

            // 업로드 파일 정보
            MultipartFile uploadFile = post.getUploadFile();

            // 업로드 파일이 있거나, 파일 삭제 체크되어 있는 경우
            if (uploadFile != null && !uploadFile.isEmpty() || post.isDeleteFile()) {
                // 기존 파일 삭제
                if (originalPost.getFileName() != null) {
                    Path filePath = Paths.get(uploadPath).resolve(originalPost.getFileName());
                    if (Files.exists(filePath)) {
                        Files.delete(filePath);
                    }
                }
            }            

            // 업로드 파일이 존재하는 경우
            if (uploadFile != null && !uploadFile.isEmpty()) {
                // 업로드 파일 이름
                String originalFileName = uploadFile.getOriginalFilename();

                // DB에 저장할 파일 이름
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

            // 게시글 수정
            boolean updated = postService.update(post);

            // 게시글 수정 성공
            if (updated) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 수정되었습니다.");
                return "redirect:/posts/" + id;
            }

            // 게시글 수정 실패
            redirectAttributes.addFlashAttribute("errorMessage", "게시글 수정에 실패했습니다.");
            return "redirect:/posts/" + id + "/update";
        } catch (IOException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드에 실패했습니다.");
            return "redirect:/posts/" + id + "/update";
        }
    }

    // 게시글 삭제 (처리, POST)
    @PostMapping("/{id}/delete")
    public String deletePost(@PathVariable("id") int id, PostDto post, RedirectAttributes redirectAttributes) {
        String uploadPath = uploadPathByOS();

        try {
            // 기존 게시글 정보 조회
            post.setId(id);
            PostDto originalPost = postService.read(post.getId());

            // 기존 파일 삭제
            if (originalPost.getFileName() != null) {
                Path filePath = Paths.get(uploadPath).resolve(originalPost.getFileName());
                if (Files.exists(filePath)) {
                    Files.delete(filePath);
                }
            }

            // 게시글 삭제
            boolean deleted = postService.delete(post);

            if (deleted) {
                redirectAttributes.addFlashAttribute("successMessage", "게시글이 삭제되었습니다.");
                return "redirect:/posts";
            }

            redirectAttributes.addFlashAttribute("errorMessage", "게시글 삭제에 실패했습니다.");
            return("redirect:/posts/" + id);
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "업로드 파일 삭제에 실패했습니다.");
            return("redirect:/posts/" + id);
        }
    }

    // 파일 다운로드
    @GetMapping("/{id}/download")
    public ResponseEntity<Resource> download(@PathVariable("id") int id) {
        String uploadPath = uploadPathByOS();

        try {
            // 게시글 정보
            PostDto post = postService.read(id);
            if (post == null || post.getFileName() == null) {
                return ResponseEntity.notFound().build();
            }

            // 파일 경로 생성
            Path filePath = Paths.get(uploadPath).resolve(post.getFileName());
            Resource resource = new UrlResource(filePath.toUri());

            // 파일이 존재하고 읽을 수 있는지 확인
            if (!resource.exists() || !resource.isReadable()) {
                return ResponseEntity.notFound().build();
            }

            // 다운로드될 파일명 설정 (원본 파일명 사용)
            String fileName = post.getOriginalFileName();

            // 한글 파일명 처리
            String encodedDownloadName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedDownloadName + "\"")
                    .body(resource);

        } catch (UnsupportedEncodingException | MalformedURLException e) {
            logger.error("파일 다운로드 오류: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
