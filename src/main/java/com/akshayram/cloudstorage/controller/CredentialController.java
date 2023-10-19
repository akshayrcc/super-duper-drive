package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import java.security.SecureRandom;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("credential")
public class CredentialController {

  @Autowired private final CredentialService credentialService;
  private final EncryptionService encryptionService;
  private final UserService userService;

  public CredentialController(
      CredentialService credentialService,
      EncryptionService encryptionService,
      UserService userService) {
    this.credentialService = credentialService;
    this.encryptionService = encryptionService;
    this.userService = userService;
  }

  /*get to the home page*/
  @GetMapping
  public String getHomePage(
      Authentication authentication,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      @ModelAttribute("newNote") NoteForm newNote,
      Model model) {
    String userName = authentication.getName();
    User user = userService.getUser(userName);
    model.addAttribute(
        "credentials", this.credentialService.getCredentialListings(user.getUserId()));
    model.addAttribute("encryptionService", encryptionService);

    return "home";
  }

  /*add new credentials*/
  @PostMapping("add-credential")
  public String newCredential(
      Authentication authentication,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      @ModelAttribute("newNote") NoteForm newNote,
      Model model) {
    String userName = authentication.getName();
    String newUrl = newCredential.getUrl();
    String credentialIdStr = newCredential.getCredentialId();
    String password = newCredential.getPassword();

    SecureRandom random = new SecureRandom();
    byte[] key = new byte[16];
    random.nextBytes(key);
    String encodedKey = Base64.getEncoder().encodeToString(key);
    String encryptedPassword = encryptionService.encryptValue(password, encodedKey);

    // check if cred is already exists
    if (credentialIdStr.isEmpty()) {
      credentialService.addCredential(
          newUrl, userName, newCredential.getUserName(), encodedKey, encryptedPassword);
    } else {
      Credential existingCredential = getCredential(Integer.parseInt(credentialIdStr));
      credentialService.updateCredential(
          existingCredential.getCredentialid(),
          newCredential.getUserName(),
          newUrl,
          encodedKey,
          encryptedPassword);
    }
    User user = userService.getUser(userName);
    model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserId()));
    model.addAttribute("encryptionService", encryptionService);
    model.addAttribute("result", "success");

    return "result";
  }

  /*fetch credential using credId*/
  @GetMapping(value = "/get-credential/{credentialId}")
  public Credential getCredential(@PathVariable Integer credentialId) {
    return credentialService.getCredential(credentialId);
  }

  /*remove credential using credId*/
  @GetMapping(value = "/delete-credential/{credentialId}")
  public String deleteCredential(
      Authentication authentication,
      @PathVariable Integer credentialId,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newNote") NoteForm newNote,
      Model model) {
    credentialService.deleteCredential(credentialId);
    String userName = authentication.getName();
    User user = userService.getUser(userName);
    model.addAttribute("credentials", credentialService.getCredentialListings(user.getUserId()));
    model.addAttribute("encryptionService", encryptionService);
    model.addAttribute("result", "success");
    return "result";
  }
}
