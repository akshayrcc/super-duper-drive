package com.akshayram.cloudstorage.services;

import com.akshayram.cloudstorage.mapper.NoteMapper;
import com.akshayram.cloudstorage.mapper.UserMapper;
import com.akshayram.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
  private final UserMapper userMapper;
  private final NoteMapper noteMapper;

  public NoteService(UserMapper userMapper, NoteMapper noteMapper) {
    this.userMapper = userMapper;
    this.noteMapper = noteMapper;
  }

  /*add a note for the given user*/
  public void addNote(String title, String description, String userName) {
    Integer userId = userMapper.getUser(userName).getUserId();
    Note note = new Note(0, title, description, userId);
    noteMapper.insert(note);
  }

  /*fetch notes for the given user*/
  public Note[] getNoteListings(Integer userId) {
    return noteMapper.getNotesForUser(userId);
  }

  /*fetch note by Id*/
  public Note getNote(Integer noteId) {
    return noteMapper.getNote(noteId);
  }

  /*remove note by Id*/
  public void deleteNote(Integer noteId) {
    noteMapper.deleteNote(noteId);
  }

  /*modify note by Id*/
  public void updateNote(Integer noteId, String title, String description) {
    noteMapper.updateNote(noteId, title, description);
  }
}
