package com.diploma.controller;

import com.diploma.auth.AuthenticationService;
import com.diploma.auth.RegisterRequest;
import com.diploma.form.Form;
import com.diploma.form.FormService;
import com.diploma.items.Items;
import com.diploma.items.ItemsService;

import com.diploma.user.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AuthenticationService service;

    @Autowired
    private ItemsService itemsService;

    @Autowired
    private FormService formService;




    @GetMapping("/adminMain")
    public String amainPage(Model model,Principal principal){
        String email = principal.getName();
        User user = service.getByEmail(email);
service.authenticate(email);
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


    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("proRequest") RegisterRequest request, Principal principal,
                               Model model
    ){

        String email = principal.getName();
        String message = "";
        String error = "";
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        if (service.checkByEmail(request.getEmail(),request.getIdCard()) == false){
        try {
            service.register(request);
            model.addAttribute("message", "Your request successfully added");
        } catch (Exception e) {
            if(request.getFirstname().isEmpty()){
                model.addAttribute("first", "Name must contain from 2 to 15 letters");
            }
            if(request.getLastname().isEmpty()){
                model.addAttribute("last", "Last Name must contain from 2 to 30 letters");
            }
            if(request.getEmail().isEmpty()){
                model.addAttribute("email", "Use valid email");
            }
            if(request.getDormitory().isEmpty()){
                model.addAttribute("dorm", "Dormitory must not be empty");
            }
           if(request.getApartment().isEmpty()){
                model.addAttribute("apart", "Apartment must not be empty");
            }

            if(request.getIdCard().isEmpty()){
                model.addAttribute("idCard", "idCard must contain 6 characters");
            }
            if(request.getPassword().isEmpty()){
                model.addAttribute("pass", "Password must not be empty");
            }
            model.addAttribute("error",
                    "Please check all rows");
        }
}
        else {
            model.addAttribute("error",
                    "User with this email or number of IdCard exists");

        }
        return "addUser";
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
        model.addAttribute("idd", id);
        return "editProfile";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute("userReq") @Valid RegisterRequest userRequest,@RequestParam("id") int id,BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/students/0";
        }

        User updatedUser = service.updateUser(userRequest, id);

        if (updatedUser != null) {
            return "redirect:/admin/students/profile/" + updatedUser.getId();
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
    public String searchUsers(@PathVariable("pageNo")int pageNo,
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

    @GetMapping("/search-resultItem/{pageNo}")
    public String searchItems(@PathVariable("pageNo")int pageNo,
                                 @RequestParam("keyword") String keyword,
                                 Model model,
                                 Principal principal){
        Page<Items> items = itemsService.searchItem(pageNo, keyword);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Search Result");
        model.addAttribute("items", items);
        model.addAttribute("size", items.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", items.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "searchItem";
    }


    @GetMapping("/search-resultForm/{pageNo}")
    public String searchForms(@PathVariable("pageNo")int pageNo,
                              @RequestParam("keyword") String keyword,
                              Model model,
                              Principal principal){
        Page<Form> form = formService.searchForm(pageNo,keyword);
        String email = principal.getName();
        User user = service.getByEmail(email);
        model.addAttribute("information", user);
        model.addAttribute("title", "Search Result");
        model.addAttribute("forms", form);
        model.addAttribute("size", form.getSize());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", form.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "searchForm";
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

    @GetMapping("/items/deleteItems/{id}")
    public String deleteItems(@PathVariable("id") Integer id) {
        Items itemsRequest = itemsService.getItems(id);
        if (itemsRequest == null) {
            return "redirect:/admin/items/0";
        }
        itemsService.deleteItemById(id);
        return "redirect:/admin/items/0";

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
