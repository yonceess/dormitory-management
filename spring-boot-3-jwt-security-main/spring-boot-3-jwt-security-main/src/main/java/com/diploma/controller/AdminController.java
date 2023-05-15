package com.diploma.controller;

import com.diploma.auth.AuthenticationService;
import com.diploma.auth.RegisterRequest;
import com.diploma.form.Form;
import com.diploma.form.FormService;
import com.diploma.items.Items;
import com.diploma.items.ItemsService;
import com.diploma.news.News;
import com.diploma.news.NewsRequest;
import com.diploma.news.NewsService;
import com.diploma.user.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthenticationService service;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private FormService formService;


    @GetMapping("/adminMain")
    public String amainPage(Model model,Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        return "admin_main";
    }

    @GetMapping("/register")
    public String register(Model model, Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("proRequest", new RegisterRequest());
        return "addUser";
    }

    @GetMapping("/registerEx")
    public String registerEx(Model model, Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("proRequest", new RegisterRequest());
        return "addUserEx";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("proRequest") RegisterRequest request, BindingResult bindingResult,
                               Model model
    ){
        System.out.println(bindingResult.hasErrors());
        if(bindingResult.hasErrors()){
            return "redirect:adminMain";
        }
        boolean value = service.register(request);

        if(value == true){
            return "redirect:/admin/students/0 ";
        }
        else {
            return "redirect:adminMain";
        }
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleConstraintViolationException(ConstraintViolationException ex, Model model) {
        return "redirect:registerEx";
    }

    @GetMapping("/students/profile/{id}")
    public String profileUser(@PathVariable("id") Integer id, Model model) {
        User userRequest = service.getUser(id);
        if (userRequest == null) {
            return "redirect:adminMain";
        }
        model.addAttribute("information", userRequest);
        return "profile1";

    }

    @GetMapping("/profile")
    public String showProfilePage(Model model, Principal principal) {

        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        return "profile1";
    }


    @GetMapping("/updateUser/{id}")
    public String updateUser(Model model, Principal principal,@PathVariable("id") Integer id){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        User userRequest = service.getUser(id);
        model.addAttribute("userReq", new RegisterRequest());
        model.addAttribute("users", userRequest);
        return "editProfile";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("userReq") @Valid RegisterRequest userRequest,BindingResult bindingResult) {
        User user = service.getByEmail(userRequest.getEmail());
        Integer in = user.getId();
        if(bindingResult.hasErrors()){
            return "redirect:/admin/students/0";
        }
        service.updateUser(userRequest);
        if (service.updateUser(userRequest) !=null) {
            return "redirect:/admin/students/profile/"+in;
        } else {
            return "redirect:/admin/students/0";
        }
    }

    @GetMapping("/students/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        User userRequest = service.getUser(id);
        if (userRequest == null) {
            return "redirect:/admin/students/0";
        }
        service.deleteUserById(id);

        return "redirect:/admin/students/0";

    }


    @GetMapping("/students")
    public String students(Model model, Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        List<User> users = service.all();
        model.addAttribute("title", "Manage Product");
        model.addAttribute("users", users);
        model.addAttribute("size", users.size());
        return "allUsers";
    }

    @GetMapping("/students/{pageNo}")
    public String productsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

        Page<User> users = service.pageUser(pageNo);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", users.getSize());
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/search-result/{pageNo}")
    public String searchProducts(@PathVariable("pageNo")int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model,
                                 Principal principal){
        Page<User> users = service.searchUser(pageNo, keyword);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Search Result");
        model.addAttribute("users", users);
        model.addAttribute("size", users.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "searchRes";
    }

    @GetMapping("/search-maleDorm/{pageNo}")
    public String searchByDorm(@PathVariable("pageNo")int pageNo,
                               Model model,
                               Principal principal){
        String dormitory ="male";
        Page<User> users = service.searchUserByDorm(pageNo, dormitory);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Search Result");
        model.addAttribute("users", users);
        model.addAttribute("size", users.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("keyword", dormitory);

        return "maleDormitory";
    }

    @GetMapping("/search-femaleDorm/{pageNo}")
    public String searchByDormFemale(@PathVariable("pageNo")int pageNo,
                                     Model model,
                                     Principal principal){
        String dormitory ="women";
        Page<User> users = service.searchUserByDorm(pageNo, dormitory);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Search Result");
        model.addAttribute("users", users);
        model.addAttribute("size", users.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("keyword", dormitory);

        return "femaleDormitory";
    }

    @GetMapping("/search-mixedDorm/{pageNo}")
    public String searchByDormMixed(@PathVariable("pageNo")int pageNo,
                                    Model model,
                                    Principal principal){
        String dormitory ="mixed";
        Page<User> users = service.searchUserByDorm(pageNo, dormitory);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Search Result");
        model.addAttribute("users", users);
        model.addAttribute("size", users.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", users.getTotalPages());
        model.addAttribute("keyword", dormitory);
        return "mixedDormitory";
    }



    @GetMapping("/items/{pageNo}")
    public String itemsPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

        Page<Items> items = itemsService.pageItem(pageNo);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", items.getSize());
        model.addAttribute("totalPages", items.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("items", items);
        return "allItems";
    }

    @GetMapping("/allItems")
    public String getAllItems(Model model, Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        List<Items> items = itemsService.findAllItems();
        model.addAttribute("items", items);

        return "allItems";
    }

    @GetMapping("/deleteItems/{id}")
    public String deleteItems(@PathVariable("id") Integer id) {
        Items itemsRequest = itemsService.getItems(id);
        if (itemsRequest == null) {
            return "redirect:/admin/items/0";
        }
        itemsService.deleteItemById(id);
        return "redirect:/admin/items/0";

    }

    @GetMapping("/saveNews")
    public String createNews(Model model,
                             Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
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
        return "redirect:/admin/news/0";

    }

    @GetMapping("/news/deleteNews/{id}")
    public String deleteNews(@PathVariable("id") Integer id) {
        News newsRequest = newsService.getNews(id);
        if (newsRequest == null) {
            return "redirect:/admin/news/0";
        }
        newsService.deleteNewsById(id);
        return "redirect:/admin/news/0";

    }

    @GetMapping("/news/{pageNo}")
    public String newssPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

        Page<News> news = newsService.pageNews(pageNo);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", news.getSize());
        model.addAttribute("totalPages", news.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("news", news);
        return "allNewsDelete";
    }


    @GetMapping("/form/deleteForm/{id}")
    public String deleteForm(@PathVariable("id") Integer id) {
        Form formRequest = formService.getForm(id);
        if (formRequest == null) {
            return "redirect:/admin/form/0";
        }
        formService.deleteFormById(id);
        return "redirect:/admin/form/0";

    }

    @GetMapping("/form/{pageNo}")
    public String formPage(@PathVariable("pageNo") int pageNo, Model model, Principal principal){

        Page<Form> form = formService.pageForm(pageNo);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Manage Product");
        model.addAttribute("size", form.getSize());
        model.addAttribute("totalPages", form.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("forms", form);
        return "allForm";
    }

}
