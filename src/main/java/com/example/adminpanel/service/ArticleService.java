package com.example.adminpanel.service;


import com.example.adminpanel.dto.ArticleDTO;
import com.example.adminpanel.entity.Article;
import com.example.adminpanel.repository.ArticleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    public List<ArticleDTO> getAllArticles() {
        List<Article> articles = articleRepository.findAll();

        return articles.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Article updateArticle(Article article) {
        return articleRepository.save(article);
    }

    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setImage(article.getImage());

        return articleDTO;
    }
}
