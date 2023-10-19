package com.akshayram.cloudstorage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/result")
public class ResultController {

  /*return the result page based on success or failure of the txn*/
  @GetMapping()
  public String resultView() {
    return "result";
  }
}
