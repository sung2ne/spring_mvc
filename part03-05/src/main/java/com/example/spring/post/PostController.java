package com.example.spring.post;

import org.springfra
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.http.HttpHeaders;
import java.nio.file.Path;
import java.nio.file.Paths;mework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PostController {

    // 게시글 등록 (화면, GET)
    @RequestMapping(value = "/posts/create", method = RequestMethod.GET)
    public String create() {
        return "post/create";
    }
}
