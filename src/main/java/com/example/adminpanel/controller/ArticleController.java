package com.example.adminpanel.controller;

import com.example.adminpanel.dto.ArticleDTO;
import com.example.adminpanel.entity.Article;
import com.example.adminpanel.service.ArticleService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/articles")
public class ArticleController {

    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Article> create(@RequestBody Article article) {
        return new ResponseEntity<>(articleService.createArticle(article), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getAllArticles() {
        return new ResponseEntity<>(articleService.getAllArticles(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<Article> update(@RequestBody Article article) {
        return new ResponseEntity<>(articleService.updateArticle(article), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public HttpStatus delete(@PathVariable Long id) {
        articleService.deleteArticle(id);
        return HttpStatus.OK;
    }
}
