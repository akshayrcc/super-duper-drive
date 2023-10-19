package com.akshayram.cloudstorage.controller;

import com.akshayram.cloudstorage.model.*;
import com.akshayram.cloudstorage.services.NoteService;
import com.akshayram.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("note")
public class NoteController {

  @Autowired private final NoteService noteService;
  private final UserService userService;

  public NoteController(NoteService noteService, UserService userService) {
    this.noteService = noteService;
    this.userService = userService;
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
    model.addAttribute("notes", this.noteService.getNoteListings(userId));

    return "home";
  }

  /*get the user Id from auth*/
  private Integer getUserId(Authentication authentication) {
    String userName = authentication.getName();
    User user = userService.getUser(userName);
    return user.getUserId();
  }

  /*add new note*/
  @PostMapping("add-note")
  public String newNote(
      Authentication authentication,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newNote") NoteForm newNote,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      Model model) {
    String userName = authentication.getName();
    String newTitle = newNote.getTitle();
    String noteIdStr = newNote.getNoteId();
    String newDescription = newNote.getDescription();

    // check if note already present and based on that add or edit the note
    if (noteIdStr.isEmpty()) {
      noteService.addNote(newTitle, newDescription, userName);
    } else {
      Note existingNote = getNote(Integer.parseInt(noteIdStr));
      noteService.updateNote(existingNote.getNoteId(), newTitle, newDescription);
    }
    Integer userId = getUserId(authentication);
    model.addAttribute("notes", noteService.getNoteListings(userId));
    model.addAttribute("result", "success");

    return "result";
  }

  /*fetch the note using noteId*/
  @GetMapping(value = "/get-note/{noteId}")
  public Note getNote(@PathVariable Integer noteId) {
    return noteService.getNote(noteId);
  }

  /*remove a note*/
  @GetMapping(value = "/delete-note/{noteId}")
  public String deleteNote(
      Authentication authentication,
      @PathVariable Integer noteId,
      @ModelAttribute("newNote") NoteForm newNote,
      @ModelAttribute("newFile") FileForm newFile,
      @ModelAttribute("newCredential") CredentialForm newCredential,
      Model model) {

    noteService.deleteNote(noteId);
    Integer userId = getUserId(authentication);
    model.addAttribute("notes", noteService.getNoteListings(userId));
    model.addAttribute("result", "success");

    return "result";
  }
}
