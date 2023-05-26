package com.diploma.controller;

import com.diploma.auth.AuthenticationService;
import com.diploma.file.FilesStorageService;
import com.diploma.form.FormRequest;
import com.diploma.form.FormService;
import com.diploma.items.Items;
import com.diploma.items.ItemsRequest;
import com.diploma.items.ItemsService;

import com.diploma.news.News;
import com.diploma.user.User;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    FilesStorageService storageService;
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
    public String registerNews(@Valid @ModelAttribute("itemsRequest") ItemsRequest request, BindingResult bindingResult,
                               Model model, Principal principal){
        String email = principal.getName();
        String message = "";
        String error = "";
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        try {
            itemsService.createItems(request,email, user.getDormitory(), user.getApartment(), user.getRoom(), user);
           model.addAttribute("message", "Your request successfully added");

        } catch (Exception e) {
            model.addAttribute("error",
                    "Please check all rows");
        }
         return "itemsForm";
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
    public String registerForm(@Valid @ModelAttribute("formRequest") FormRequest request,Model model, Principal principal){
        String email = principal.getName();
        String message = "";
        String error = "";
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        try {
            formService.createForm(request,email,user);
            model.addAttribute("message", "Your request successfully added");
        } catch (Exception e) {
            model.addAttribute("error",
                    "Please check all rows");
        }
        return "nightForm";
    }





}
