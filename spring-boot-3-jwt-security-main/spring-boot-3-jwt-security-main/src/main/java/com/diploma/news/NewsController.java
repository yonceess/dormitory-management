package com.diploma.news;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/v1/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/saveNews")
    public String createNews(Model model){
        model.addAttribute("newsRequest", new NewsRequest());
        return "newsForm";
    }

    @PostMapping("/saveNewsPost")
    public String registerNews(@ModelAttribute("newsRequest") NewsRequest request,
                               @RequestParam("imageProduct") MultipartFile imageProduct,
                               RedirectAttributes attributes){
        try {
            newsService.uploadImage(imageProduct, request);
            attributes.addFlashAttribute("success", "Add successfully!");
        }catch (Exception e){
            e.printStackTrace();
            attributes.addFlashAttribute("error", "Failed to add!");
        }
        return "redirect:/api/v1/news/news/0";

    }


    @GetMapping("/allnews/{pageNo}")
    public String newsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

        Page<News> news = newsService.pageNews(pageNo);

        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", news.getSize());
        model.addAttribute("totalPages", news.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("news", news);
        return "news";
    }



    @GetMapping("/deleteNews")
    public ResponseEntity deleteNewsById(@RequestParam int id){
        newsService.deleteNewsById(id);
        return ResponseEntity.ok("Information deleted");
    }

    @GetMapping("/allNewsDelete")
    public String getAllNewsDelete(Model model){
        List<News> news = newsService.findAllNews();
        model.addAttribute("news", news);

        return "allNewsDelete";
    }

    @GetMapping("/news/deleteNews/{id}")
    public String deleteNews(@PathVariable("id") Integer id) {
        News newsRequest = newsService.getNews(id);
        if (newsRequest == null) {
            return "Product not found";
        }
        newsService.deleteNewsById(id);
        return "redirect:/api/v1/news/news/0";

    }


    @GetMapping("/allnews/detailedNews/{id}")
    public String detailedNews(@PathVariable("id") Integer id, Model model){
        News news= newsService.getNews(id);
        byte[] imageData= newsService.downloadImage(news.getFileName());
       ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
        model.addAttribute("news", news);
        return "detailedNews";

    }
    @GetMapping("/news/{pageNo}")
    public String newssPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

        Page<News> news = newsService.pageNews(pageNo);

        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", news.getSize());
        model.addAttribute("totalPages", news.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("news", news);
        return "allNewsDelete";
    }



}
