package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.FileForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/home")
public class HomeController {

  @Autowired private final FileService fileService;
  private final UserService userService;
  private final NoteService noteService;
  private final CredentialService credentialService;
  private final EncryptionService encryptionService;

  public HomeController(
      FileService fileService,
      UserService userService,
      NoteService noteService,
      CredentialService credentialService,
      EncryptionService encryptionService) {
    this.fileService = fileService;
    this.userService = userService;
    this.noteService = noteService;
    this.credentialService = credentialService;
    this.encryptionService = encryptionService;
  }

  /*get to the home page*/
  @GetMapping
  public String getHomePage(
      Authentication authentication,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newNote") NoteForm newNote,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      Model model) {
    Integer userId = getUserId(authentication);
    model.addAttribute("files", this.fileService.getFileListings(userId));
    model.addAttribute("notes", noteService.getNoteListings(userId));
    model.addAttribute("credentials", credentialService.getCredentialListings(userId));
    model.addAttribute("encryptionService", encryptionService);

    return "home";
  }

  /*fetch the userId for the given user*/
  private Integer getUserId(Authentication authentication) {
    String userName = authentication.getName();
    User user = userService.getUser(userName);
    return user.getUserId();
  }

  /*add a new file for the given user*/
  @PostMapping
  public String newFile(
      Authentication authentication,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newNote") NoteForm newNote,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      Model model)
      throws IOException {
    // fetch user id and name
    String userName = authentication.getName();
    User user = userService.getUser(userName);
    Integer userId = user.getUserId();

    // fetch files for given user
    String[] fileListings = fileService.getFileListings(userId);
    MultipartFile multipartFile = newFile.getFile();
    String fileName = multipartFile.getOriginalFilename();

    // check if new file exists already
    boolean fileIsDuplicate = false;
    for (String fileListing : fileListings) {
      if (fileListing.equals(fileName)) {
        fileIsDuplicate = true;
        break;
      }
    }
    // update the status model accordingly
    if (!fileIsDuplicate) {
      fileService.addFile(multipartFile, userName);
      model.addAttribute("result", "success");
    } else {
      model.addAttribute("result", "error");
      model.addAttribute("message", "You have tried to add a duplicate file.");
    }
    model.addAttribute("files", fileService.getFileListings(userId));

    return "result";
  }

  /*fetch a file using filename*/
  @GetMapping(value = "/get-file/{fileName}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
  public @ResponseBody byte[] getFile(@PathVariable String fileName) {
    return fileService.getFile(fileName).getFileData();
  }

  /*remove a file using filename*/
  @GetMapping(value = "/delete-file/{fileName}")
  public String deleteFile(
      Authentication authentication,
      @PathVariable String fileName,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newNote") NoteForm newNote,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      Model model) {
    fileService.deleteFile(fileName);
    Integer userId = getUserId(authentication);
    model.addAttribute("files", fileService.getFileListings(userId));
    model.addAttribute("result", "success");

    return "result";
  }
}
