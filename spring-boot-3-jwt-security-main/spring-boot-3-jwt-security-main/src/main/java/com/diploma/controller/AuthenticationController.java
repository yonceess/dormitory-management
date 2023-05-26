package com.diploma.controller;

import com.diploma.auth.AuthenticationRequest;
import com.diploma.auth.AuthenticationService;
import com.diploma.user.User;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
        return "mainPage";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/login")
    public String login(){
        return "signing";
    }





    @GetMapping("/authenticate")
    public String auth(Model model){
        model.addAttribute("authRequest", new AuthenticationRequest());
        return "signing";
    }

  @PostMapping("/authenticateUser")
  public String authenticate(@ModelAttribute("authRequest") AuthenticationRequest request) {

    boolean role = service.checkRole(request.getEmail());
    if( role==true){
        System.out.println("done");
        return "redirect:/admin/adminMain";
    }
    else if( role==false){
        System.out.println("wht");
        return "redirect:main";
      }
    else {
        return "redirect:main";
    }
  }




}
