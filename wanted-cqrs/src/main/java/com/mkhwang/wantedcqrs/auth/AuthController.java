package com.mkhwang.wantedcqrs.auth;

import com.mkhwang.wantedcqrs.auth.dto.RegisterDto;
import com.mkhwang.wantedcqrs.auth.exception.DuplicateUsernameException;
import com.mkhwang.wantedcqrs.user.application.UserRegisterService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {
  private final UserRegisterService userRegisterService;

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

  @PostMapping("/register")
  public String register(@Valid @RequestBody RegisterDto registerDto, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "register";
    }

    try {
      userRegisterService.register(registerDto);
    } catch (DuplicateUsernameException e) {
      bindingResult.rejectValue("username", "signup.form.email.duplicated");
      return "register";
    }

    return "redirect:/login";
  }
}
