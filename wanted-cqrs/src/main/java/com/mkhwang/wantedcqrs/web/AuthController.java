package com.mkhwang.wantedcqrs.web;

import com.mkhwang.wantedcqrs.web.dto.RegisterDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

  @GetMapping("/login")
  public String login(Model model) {
    return "login.html";
  }

  @GetMapping("/register")
  public String showRegister(Model model) {
    RegisterDto registerDto = new RegisterDto();
    model.addAttribute("registerDto", registerDto);
    return "register.html";
  }
}
