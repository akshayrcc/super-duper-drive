package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping("/signup")
public class SignupController {

  private final UserService userService;

  public SignupController(UserService userService) {
    this.userService = userService;
  }

  /*Get signup pages loaded*/
  @GetMapping()
  public String signupView() {
    return "signup";
  }

  /*Post signup action*/
  @PostMapping()
  public String signupUser(@ModelAttribute User user, Model model) {
    String signupError = null;

    /*check if username already exists*/
    if (!userService.isUsernameAvailable(user.getUsername())) {
      signupError = "The username already exists.";
    }

    /*if no row added post service all, notify with error*/
    if (signupError == null) {
      int rowsAdded = userService.createUser(user);
      if (rowsAdded < 0) {
        signupError = "There was an error signing you up. Please try again.";
      }
    }

    /*return final status*/
    if (signupError == null) {
      model.addAttribute("signupSuccess", true);
    } else {
      model.addAttribute("signupError", signupError);
    }

    return "signup";
  }
}
