package com.diploma.controller;

import com.diploma.auth.AuthenticationRequest;
import com.diploma.auth.AuthenticationService;
import com.diploma.user.User;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController  {

  private final AuthenticationService service;

    @GetMapping("/main")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }


    @GetMapping("/authenticate")
    public String auth(Model model){
        model.addAttribute("authRequest", new AuthenticationRequest());
        return "signNewIn";
    }

  @PostMapping("/authenticateUser")
  public String authenticate(@ModelAttribute("authRequest") AuthenticationRequest request) {
    boolean value= service.authenticate(request);
boolean role = service.checkRole(request.getEmail());
      System.out.println(value);
      System.out.println(role);
    if(value == true && role==true){
        return "redirect:/admin/adminMain";
    }
    else if(value == true && role==false){
          return "redirect:/user/allnews/0";
      }
    else {
        return "redirect:main";
    }
  }




}
