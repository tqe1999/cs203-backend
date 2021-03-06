package csd.cs203project.controller.newsarticle;

import csd.cs203project.model.NewsArticle;
import csd.cs203project.service.newsarticle.NewsArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class NewsArticleController {
    private NewsArticleService newsArticleService;

    @Autowired
    public NewsArticleController(NewsArticleService newsArticleService) {
        this.newsArticleService = newsArticleService;
    }

    
    /** 
     * Allows administrators to add a news article
     * @param newsArticle
     * @return NewsArticle
     */
    @PostMapping("/newsArticle")
    public NewsArticle addNewsArticle (@RequestBody NewsArticle newsArticle) {
        return newsArticleService.addNewsArticle(newsArticle);
    }

    
    /** 
     * Get the latest news articles 
     * @return List<NewsArticle> of latest news articles
     */
    @GetMapping("/newsArticle")
    public List<NewsArticle> getNewsArticles() {
        return newsArticleService.getLatestArticles();
    }

}
