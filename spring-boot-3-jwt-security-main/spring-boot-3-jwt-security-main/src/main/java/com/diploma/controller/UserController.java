package com.diploma.controller;

import com.diploma.auth.AuthenticationService;
import com.diploma.form.FormRequest;
import com.diploma.form.FormService;
import com.diploma.items.Items;
import com.diploma.items.ItemsRequest;
import com.diploma.items.ItemsService;
import com.diploma.news.News;
import com.diploma.news.NewsService;
import com.diploma.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final AuthenticationService service;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private FormService formService;


    @GetMapping("/createItem")
    public String createNews(Model model, Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("itemsRequest", new ItemsRequest());
        return "itemsForm";
    }

    @PostMapping("/createItemPost")
    public String registerNews(@ModelAttribute("itemsRequest") ItemsRequest request, Principal principal, RedirectAttributes redirAttrs){
        String email = principal.getName();
        itemsService.createItems(request,email);
        redirAttrs.addFlashAttribute("message", "Your request was successfully sent.");
        return "redirect:createItem";
    }


    @GetMapping("/allnews/{pageNo}")
    public String newsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

        Page<News> news = newsService.pageNews(pageNo);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", news.getSize());
        model.addAttribute("totalPages", news.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("news", news);
        return "news";
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

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {

        String email = principal.getName();
        System.out.println(principal.getName());
        User user = service.getByEmail(email);
        List<Items> itemsList = itemsService.usersItems(email);
        model.addAttribute("items", itemsList);
        model.addAttribute("information", user);
        return "userProfile";
    }

    @GetMapping("/createForm")
    public String createForm(Model model, Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("formRequest", new FormRequest());
        return "nightForm";
    }

    @PostMapping("/createFormPost")
    public String registerForm(@ModelAttribute("formRequest") FormRequest request, Principal principal, RedirectAttributes redirAttrs){
        String email = principal.getName();
        formService.createForm(request,email);
        redirAttrs.addFlashAttribute("message", "Your request was successfully sent.");
        return "redirect:createForm";
    }
}
